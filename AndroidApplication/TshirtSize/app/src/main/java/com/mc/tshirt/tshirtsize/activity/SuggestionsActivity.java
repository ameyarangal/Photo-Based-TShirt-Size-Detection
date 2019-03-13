package com.mc.tshirt.tshirtsize.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mc.tshirt.tshirtsize.R;

import java.io.File;
import java.util.ArrayList;

public class SuggestionsActivity extends AppCompatActivity {
    private ArrayList<String> singleList1;
    private ArrayList<String> singleList2;
    private ArrayList<String> singleList3;
    private ArrayList<String> singleList4;
    private ArrayList<String> singleList5;
    private ArrayList<String> singleList6;
    private ListView mShowSuggestionsListView;
    private String mTshirtSize;
    private String mPhotoFilePath;
    private TextView mUserSizeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        populateSizeLists();
        initializeView();
    }
    private void initializeView(){
        mTshirtSize = getIntent().getStringExtra("tshirt_size");
        mPhotoFilePath = getIntent().getStringExtra("file_path");
        mShowSuggestionsListView = findViewById(R.id.suggestions_lv);
        mUserSizeTextView = findViewById(R.id.user_size_tv);

        mUserSizeTextView.setText("Your Tshirt size is " + mTshirtSize);

        if (mTshirtSize.equalsIgnoreCase("S")){
            mShowSuggestionsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleList1));
        } else if (mTshirtSize.equalsIgnoreCase("M")){
            mShowSuggestionsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleList2));
        } else if (mTshirtSize.equalsIgnoreCase("L")){
            mShowSuggestionsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleList3));
        } else if (mTshirtSize.equalsIgnoreCase("XL")){
            mShowSuggestionsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleList4));
        } else if (mTshirtSize.equalsIgnoreCase("XXL")){
            mShowSuggestionsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleList4));
        } else if (mTshirtSize.equalsIgnoreCase("XXXL")){
            mShowSuggestionsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleList4));
        } else if (mTshirtSize.equalsIgnoreCase("XXXXL")){
            mShowSuggestionsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, singleList4));
        }
        File file = new File(mPhotoFilePath);
        boolean deleted = file.delete();
    }

    private void populateSizeLists(){

        singleList1 = new ArrayList<String>();
        singleList2 = new ArrayList<String>();
        singleList3 = new ArrayList<String>();
        singleList4 = new ArrayList<String>();
        singleList5 = new ArrayList<String>();
        singleList6 = new ArrayList<String>();

        singleList1.add("S: Aeropostale");
        singleList1.add("S: Lucky");
        singleList1.add("S: Club Monaco");
        singleList1.add("S: ASOS");
        singleList1.add("S: Uniqlo");
        singleList1.add("S: Hollister");
        singleList1.add("S: Ecko Unlimited");
        singleList1.add("M: Armani");
        singleList1.add("M: Diesel");
        singleList1.add("S: Hurley");
        singleList1.add("S: Hudson Jeans");
        singleList1.add("S: Joe Brown's");
        singleList1.add("M: Buckle BKE");
        singleList1.add("L: Patagonia");
        singleList1.add("S: Calvin Klein");
        singleList1.add("S: Ecko MMA");

        singleList2.add("M: Aeropostale");
        singleList2.add("L: Hurley");
        singleList2.add("XL: Armani");
        singleList2.add("L: ASOS");
        singleList2.add("XL: Diesel");
        singleList2.add("L: Ecko Unlimited");
        singleList2.add("L: Joe's Jeans");
        singleList2.add("M: Uniqlo");
        singleList2.add("M: Hudson Jeans");
        singleList2.add("M: Lucky");
        singleList2.add("M: Joe Brown's");
        singleList2.add("M: Michael Kors");
        singleList2.add("M: Calvin Klein");
        singleList2.add("M: Buckle BKE");
        singleList2.add("L: Ecko MMA");
        singleList2.add("XXX Large: Patagonia");
        singleList2.add("M: Nike");

        singleList3.add("L: Aeropostale");
        singleList3.add("XL: Lucky");
        singleList3.add("S: Club Monaco");
        singleList3.add("XL: ASOS");
        singleList3.add("S: Uniqlo");
        singleList3.add("S: Hollister");
        singleList3.add("XL: Ecko Unlimited");
        singleList3.add("XXL: Armani");
        singleList3.add("XXL: Diesel");
        singleList3.add("XL: Hurley");
        singleList3.add("S: Hudson Jeans");
        singleList3.add("L: Joe Brown's");
        singleList3.add("L: Buckle BKE");
        singleList3.add("L: Patagonia");
        singleList3.add("XL: Calvin Klein");
        singleList3.add("XL: Ecko MMA");
        singleList3.add("L: Adidas");
        singleList3.add("L: Nike");
        singleList3.add("Rag and Bone: XL");

        singleList4.add("XL: Aeropostale");
        singleList4.add("XXL: Lucky");
        singleList4.add("2XL: Ecko Unlimited");
        singleList4.add("XXL: Armani");
        singleList4.add("XXL: Diesel");
        singleList4.add("XXL: Hurley");
        singleList4.add("XL: Joe Brown's");
        singleList4.add("L: Buckle BKE");
        singleList4.add("XXX-Large: Patagonia");
        singleList4.add("XXL: Calvin Klein");
        singleList4.add("2XL: Ecko MMA");
        singleList4.add("L: Adidas");
        singleList4.add("L: Nike");
        singleList4.add("Rag and Bone: XL");
        singleList4.add("XL: Tommy Bahama");
        singleList4.add("XL: Under Armour");
        singleList4.add("XXL: lululemon athletica");
        singleList4.add("XXL: Lucky");

        //XXL
        singleList5.add("XXL: Under Armour");
        singleList5.add("XXL: Aeropostale");
        singleList5.add("3XL: Ecko Unlimited");
        singleList5.add("XLX: Tommy Bahama");
        singleList5.add("2XL: American Apparel");
        singleList5.add("XXL: Joe Brown's");
        singleList5.add("XXL: Michael Kors");
        singleList5.add("3XL: Ecko MMA");
        singleList5.add("XL: Calvin Klein");
        singleList5.add("XXXL: Patagonia");

//XXXL
        singleList6.add("3XL: Under Armour");
        singleList6.add("XXXL: Aeropostale");
        singleList6.add("XXL: Nike");
        singleList6.add("XXXL: Patagonia");
    }

}
