package com.example.qr_scanner_and_image_similarity_detection.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qr_scanner_and_image_similarity_detection.FindOwner;
import com.example.qr_scanner_and_image_similarity_detection.R;

import java.util.ArrayList;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;


public class Upload_photo_Fragment extends Fragment {

    ImageView Item_img;
    Spinner Categories_spiner;
    ListView Mylist;
    CustomAdapter customadapter = null;

    ArrayList<Bitmap> Images_lst = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent CameraIntent = new Intent(ACTION_IMAGE_CAPTURE);
        startActivityForResult(CameraIntent,1888);

        View view = inflater.inflate(R.layout.fragment_upload_photo_, container, false);
        Item_img = view.findViewById(R.id.uploadfrag_ItmImg);
        Categories_spiner = view.findViewById(R.id.UploadFrag_Categ_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.ItemCategories, android.R.layout.simple_spinner_dropdown_item);
        Categories_spiner.setAdapter(adapter);
        Categories_spiner.setPrompt("Select Category");

        Mylist = view.findViewById(R.id.cutting_imgs_lst);
        customadapter = new CustomAdapter(getContext(),R.layout.cutimg_layout,Images_lst);
        Mylist.setAdapter(customadapter);

        Mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), FindOwner.class);
                intent.putExtra("SelectedImage",Images_lst.get(position));
                intent.putExtra("index",position);
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1888 && resultCode == getActivity().RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Item_img.setImageBitmap(bitmap);

            //test to show images
            for(int i=0;i<5;i++){
                Images_lst.add(bitmap);
            }
        }
    }

    //custom adapter for list to contain images after segmentation
    private class CustomAdapter extends ArrayAdapter<Bitmap> {

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<Bitmap> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public int getCount() {
            return Images_lst.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.cutimg_layout, parent,false);

            ImageView Image = view.findViewById(R.id.img_cuting);
            Image.setImageBitmap(Images_lst.get(position));

            return view;
        }
    }
}