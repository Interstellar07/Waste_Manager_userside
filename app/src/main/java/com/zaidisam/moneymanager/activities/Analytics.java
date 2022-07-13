package com.zaidisam.moneymanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zaidisam.moneymanager.R;

import java.util.ArrayList;
import java.util.List;


public class Analytics extends AppCompatActivity {
    private DatabaseReference budgetref;
    private FirebaseAuth mAuth;
    AnyChartView anyChartView;
    private TextView totaltrnsport,food,apparel,home,totalspent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        totaltrnsport= findViewById(R.id.analyticstrasnportAmount);
        totalspent=findViewById(R.id.analytictotalAmount);
        food = findViewById(R.id.analyticsFoodAmount);
        anyChartView = findViewById(R.id.chart);
        apparel=findViewById(R.id.analyticsApparelAmount);
        home= findViewById(R.id.analyticsHouseAmount);
        mAuth = FirebaseAuth.getInstance();
        budgetref= FirebaseDatabase.getInstance().getReference().child("Transactions").child(mAuth.getCurrentUser().getUid());
        budgetref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] items={"Food","Transport","Apparel","House"};int[] pieamount = new int[4];
                String month1="July",month2;

                int salary=0,expense=0,savings =0,total=0,k=0;String typeof;
                int foodtota=0,trasnporttota=0,homettl=0,apparelttl=0;
                String item;
                for(DataSnapshot snap: snapshot.getChildren()) {
                    Data data = snap.getValue(Data.class);
                    month2 = data.getMonth();



                        if (data.getType().equals("Expense") && month2.equals(month1)) {
                            total += data.getAmount();
                        }
                        item = data.getItem().toString();

                        if (item.equals("Food")&& month2.equals(month1)) {
                            foodtota += data.getAmount();
                            pieamount[k] = foodtota;
                            k++;
                        }
                        if (item.equals("Transport")&& month2.equals(month1)) {
                            trasnporttota += data.getAmount();
                            pieamount[k] = trasnporttota;
                            k++;
                        }
                        if (item.equals("Apparel")&& month2.equals(month1)) {
                            apparelttl += data.getAmount();
                            pieamount[k] = apparelttl;
                            k++;
                        }
                        if (item.equals("House")&& month2.equals(month1)) {
                            homettl += data.getAmount();
                            pieamount[k] = homettl;
                            k++;
                        }






                }

                Pie pie = AnyChart.pie();
                List<DataEntry> dataEntryList= new ArrayList<>();
                for(int m=0;m<items.length;m++)
                    dataEntryList.add(new ValueDataEntry(items[m],pieamount[m]));
                pie.data(dataEntryList);
                anyChartView.setChart(pie);

                totalspent.setText("₹ "+String.valueOf(total));
                totaltrnsport.setText("Spent: ₹ "+String.valueOf(trasnporttota));
               food.setText("Spent: ₹ "+String.valueOf(foodtota));
                apparel.setText("Spent: ₹ "+String.valueOf(apparelttl));
                home.setText("Spent: ₹ "+String.valueOf(homettl));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    /*private void fetchdata(DataSnapshot snapshot,DataSnapshot snap)
    {
        String[] items={"Food","Transport","Apparel","House"};int[] pieamount = new int[4];

        int salary=0,expense=0,savings =0,total=0,k=0;String typeof;
        int foodtota=0,trasnporttota=0,homettl=0,apparelttl=0;
        String item;

         Data data = snap.getValue(Data.class);

                    if (data.getType().equals("Expense")) {
                        total += data.getAmount();
                    }
                    item = data.getItem().toString();

                    if (item.equals("Food")) {
                        foodtota += data.getAmount();
                        pieamount[k] = foodtota;
                        k++;
                    }
                    if (item.equals("Transport")) {
                        trasnporttota += data.getAmount();
                        pieamount[k] = trasnporttota;
                        k++;
                    }
                    if (item.equals("Apparel")) {
                        apparelttl += data.getAmount();
                        pieamount[k] = apparelttl;
                        k++;
                    }
                    if (item.equals("House")) {
                        homettl += data.getAmount();
                        pieamount[k] = homettl;
                        k++;
                    }







        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntryList= new ArrayList<>();
        for(int m=0;m<items.length;m++)
            dataEntryList.add(new ValueDataEntry(items[m],pieamount[m]));
        pie.data(dataEntryList);
        anyChartView.setChart(pie);

        totalspent.setText("₹ "+String.valueOf(total));
        totaltrnsport.setText("Spent: ₹ "+String.valueOf(trasnporttota));
        food.setText("Spent: ₹ "+String.valueOf(foodtota));
        apparel.setText("Spent: ₹ "+String.valueOf(apparelttl));
        home.setText("Spent: ₹ "+String.valueOf(homettl));


    }*/

}