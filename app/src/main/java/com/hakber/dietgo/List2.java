package com.hakber.dietgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List2 extends AppCompatActivity {
    private String[][] list2 =
            {{"Cips","Gevrek","Kraker","Krakerler","Mısır Cipsi","Müsli Barlar","Patates Cipsi","Patlamış Mısır","Pirinçli Kek","Sakız","Sandviçler"
            ,"Suşi","Tahıl Barları"}, {"Burger","Hamburger","Köriler","Mücver","Patates Kızartması","Peynirli Pizza","Pizza","Soğan Halkaları",
                    "Sosisli Sandviç","Tavuk Parçaları","Vejetaryan Burger"},{"Antapot","Alabalık","..."},{"Bamya Çorbası","Çorbalar","Domates Çorbası",
            "Et Suyu","Güveç","Nohut Çorbası","Tavuk Çorbası"},{"Beyaz Ekmek","..."},{"Biftek","Dana Eti","..."},{"Barbunya","..."},
                    {"Alkol","Beyaz Şarap","..."},{"Antep Fıstığı","..."},{"Beyaz Pirinç","..."},{"Ahududu","..."},{"Bahçe Salatası","..."},{"Bamya","..."},
                    {"Akçaağaç Şurubu","..."},{"Amerikan Peyniri","..."},{"Buz Dondurma","..."},{"Çırpılmış Yumurta","..."},{"Arpa","..."}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //(A) adımı
        ListView listemiz=(ListView) findViewById(R.id.list2View2);
            final String index = getIntent().getExtras().getString("index");
            int indexInt=Integer.parseInt(index);


        //(B) adımı
        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, list2[indexInt]);

        //(C) adımı
        listemiz.setAdapter(veriAdaptoru);

        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent i = new Intent(List2.this, foodList.class);
                i.putExtra("index",index+String.valueOf(position));
                startActivity(i);

            }
        });
    }

}
