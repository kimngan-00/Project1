package com.example.project1.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.daos.DAO_Hotel;
import com.example.project1.model.Hotel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Add_Post extends Fragment {
    View view;
    EditText edDescription, edAddress, edNameLocate;
    Button btnPost;
    ImageView imgLocation;
    AutoCompleteTextView acPlace;
    ArrayAdapter acAdapter;
    public List<String> placesList;
    DatabaseReference dbPlaces;
    int PICK_IMAGE_REQUEST = 10;
    Uri filePath;
    DAO_Hotel dao_hotel = new DAO_Hotel(getActivity(), this);
    StorageReference storageReference;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            imgLocation.setImageURI(data.getData());
        }

    }

    public Fragment_Add_Post() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_post, container, false);


//        Init
        edAddress = (EditText) view.findViewById(R.id.fAddPost_edAddress);
        edDescription = (EditText) view.findViewById(R.id.fAddPost_edDescription);
        edNameLocate = (EditText) view.findViewById(R.id.fAddPost_edNameLocate);
        btnPost = (Button) view.findViewById(R.id.fAddPost_btnPost);
        imgLocation = (ImageView) view.findViewById(R.id.fAddPost_imgLocation);
        acPlace = (AutoCompleteTextView) view.findViewById(R.id.fAddPost_acPlaces);

//        Action


//        Autocomplete
        placesList = new ArrayList<>();
        dbPlaces = FirebaseDatabase.getInstance().getReference("places");

        dbPlaces.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placesList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String place = (String) ds.getValue();
                    placesList.add(place);
                }
                acPlace.setThreshold(1);
                acAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, placesList);
                acPlace.setAdapter(acAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        acPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String namePlace = (String) parent.getItemAtPosition(position);
                acPlace.setText(namePlace);
            }
        });


        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Hotel hotel = new Hotel();
                String name = edNameLocate.getText().toString();
                String address = edAddress.getText().toString();
                String description = edDescription.getText().toString();
                String namePlace = acPlace.getText().toString();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
                String date = formatter.format(calendar.getTime()) + "";

                hotel.setName_Place(namePlace);
                hotel.setAddress_Hotel(address);
                hotel.setName_Hotel(name);
                hotel.setDescription_Hotel(description);
                hotel.setPubDate_Hotel(date);

                final StorageReference ref = FirebaseStorage.getInstance()
                        .getReference("hotel/" + UUID.randomUUID().toString());
                imgLocation.setDrawingCacheEnabled(true);
                imgLocation.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imgLocation.getDrawable()).getBitmap();
                ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bAOS);
                byte[] data = bAOS.toByteArray();
                UploadTask uploadTask = ref.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                hotel.setImage_Hotel(String.valueOf(uri));
                                dao_hotel.insert(hotel);
                                Bundle bundle = getArguments();
                                if (bundle != null) {
                                    final String fragment = bundle.getString("fragment");
                                    switch (fragment) {
                                        case "hotel":
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.main_frameLayout, new Fragment_Hotel())
                                                    .commit();
                                            break;
                                        case "food":
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.main_frameLayout, new Fragment_Food())
                                                    .commit();
                                            break;
                                    }

                                } else {
                                    Toast.makeText(getContext(), "Không nhận được dữ liệu", Toast.LENGTH_SHORT).show();
                                }

                                Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });


        return view;
    }


    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }


    private void uploadImage() {

        if (filePath != null) {
            final StorageReference ref = storageReference.child("hotel/" + UUID.randomUUID().toString());
            ref.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String URl = uri.toString();
                                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

}
