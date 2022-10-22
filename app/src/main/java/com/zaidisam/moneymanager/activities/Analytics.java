package com.zaidisam.moneymanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Analytics extends AppCompatActivity {
    private DatabaseReference budgetref;
    private FirebaseAuth mAuth;
    AnyChartView anyChartView;
    String month1,month2 , month_name;
    private TextView totaltrnsport,food,apparel,home,totalspent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        totaltrnsport= findViewById(R.id.analyticstrasnportAmount);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
       month_name = month_date.format(cal.getTime());
        month1=month_name;
        totalspent=findViewById(R.id.analytictotalAmount);
        food = findViewById(R.id.analyticsFoodAmount);
        anyChartView = findViewById(R.id.chart);
        apparel=findViewById(R.id.analyticsApparelAmount);
        home= findViewById(R.id.analyticsHouseAmount);
        mAuth = FirebaseAuth.getInstance();
        budgetref= FirebaseDatabase.getInstance().getReference().child("Transactions").child(mAuth.getCurrentUser().getUid());
//        budgetref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String[] items={"Food","Transport","Apparel","House","Education","Health","Personal","Charity"};int[]
//                        pieamount = new int[8];
//
//
//                int salary=0,expense=0,savings =0,total=0,k=0;String typeof;
//                int foodtota=0,trasnporttota=0,homettl=0,apparelttl=0,educationttl=0,healthtttl=0,personalttl=0, chairtyttl=0;
//                String item;
//                for(DataSnapshot snap: snapshot.getChildren()) {
//                    Data data = snap.getValue(Data.class);
//
//                    month2 = data.getMonth();
//
//
//                        if (data.getType().equals("Expense") && month2.equals(month1)) {
//                            total += data.getAmount();
//                        }
//                        item = data.getItem().toString();
//
//                        if (item.equals("Food")&& month2.equals(month1)) {
//                            foodtota += data.getAmount();
//                            pieamount[k] = foodtota;
//                            k++;
//                        }
//                        if (item.equals("Transport")&& month2.equals(month1)) {
//                            trasnporttota += data.getAmount();
//                            pieamount[k] = trasnporttota;
//                            k++;
//                        }
//                        if (item.equals("Apparel")&& month2.equals(month1)) {
//                            apparelttl += data.getAmount();
//                            pieamount[k] = apparelttl;
//                            k++;
//                        }
//                        if (item.equals("House")&& month2.equals(month1)) {
//                            homettl += data.getAmount();
//                            pieamount[k] = homettl;
//                            k++;
//                        }
//                        if(item.equals("Education")&& month2.equals(month1))
//                        {
//                            educationttl+=data.getAmount();
//                            pieamount[k] = educationttl;
//                            k++;
//                        }
//                        if(item.equals("Health")&& month2.equals(month1))
//                        {
//                            healthtttl+= data.getAmount();
//                            pieamount[k] = healthtttl;
//                            k++;
//                        }
//                        if(item.equals("Personal")&& month2.equals(month1))
//                        {
//                            personalttl+=data.getAmount();
//                            pieamount[k] = personalttl;
//                            k++;
//
//                        }
//                        if(item.equals("Charity") &&month2.equals(month1))
//                        {
//                            chairtyttl+=data.getAmount();
//                            pieamount[k]= chairtyttl;
//                            k++;
//                        }
//
//
//
//
//
//
//                }
//
//
//                Pie pie = AnyChart.pie();
//                List<DataEntry> dataEntryList= new ArrayList<>();
//                for(int m=0;m<items.length;m++)
//                    dataEntryList.add(new ValueDataEntry(items[m],pieamount[m]));
//                pie.data(dataEntryList);
//                anyChartView.setChart(pie);
//
//                totalspent.setText("₹ "+String.valueOf(total));
//                totaltrnsport.setText("Spent: ₹ "+String.valueOf(trasnporttota));
//               food.setText("Spent: ₹ "+String.valueOf(foodtota));
//                apparel.setText("Spent: ₹ "+String.valueOf(apparelttl));
//                home.setText("Spent: ₹ "+String.valueOf(homettl));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.analytics_menu,menu);
        return true;
    }

//    public void setmonth(MenuItem item) {
//
//        System.out.println("TEST");
//
//
//       AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View myView = inflater.inflate(R.layout.choose_month,null);
//        myDialog.setView(myView);
//        final AlertDialog dialog = myDialog.create();
//        dialog.show();
//        dialog.setCancelable(false);
//        Button cancel = myView.findViewById(R.id.cancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//         final Button save = myView.findViewById(R.id.set);
//        final Spinner itemSpinner = myView.findViewById(R.id.itemsspinner);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                month1 = itemSpinner.getSelectedItem().toString();
//                budgetref= FirebaseDatabase.getInstance().getReference().child("Transactions").child(mAuth.getCurrentUser().getUid());
//                budgetref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String[] items={"Food","Transport","Apparel","House","Education","Health","Personal","Charity"};int[]
//                                pieamount = new int[8];
//
//
//                        int salary=0,expense=0,savings =0,total=0,k=0;String typeof;
//                        int foodtota=0,trasnporttota=0,homettl=0,apparelttl=0,educationttl=0,healthtttl=0,personalttl=0, chairtyttl=0;
//                        String item;
//                        for(DataSnapshot snap: snapshot.getChildren()) {
//                            Data data = snap.getValue(Data.class);
//
//                            month2 = data.getMonth();
//
//
//                            if (data.getType().equals("Expense") && month2.equals(month1)) {
//                                total += data.getAmount();
//                            }
//                            item = data.getItem().toString();
//
//                            if (item.equals("Food")&& month2.equals(month1)) {
//                                foodtota += data.getAmount();
//                                pieamount[k] = foodtota;
//                                k++;
//                            }
//                            if (item.equals("Transport")&& month2.equals(month1)) {
//                                trasnporttota += data.getAmount();
//                                pieamount[k] = trasnporttota;
//                                k++;
//                            }
//                            if (item.equals("Apparel")&& month2.equals(month1)) {
//                                apparelttl += data.getAmount();
//                                pieamount[k] = apparelttl;
//                                k++;
//                            }
//                            if (item.equals("House")&& month2.equals(month1)) {
//                                homettl += data.getAmount();
//                                pieamount[k] = homettl;
//                                k++;
//                            }
//                            if(item.equals("Education")&& month2.equals(month1))
//                            {
//                                educationttl+=data.getAmount();
//                                pieamount[k] = educationttl;
//                                k++;
//                            }
//                            if(item.equals("Health")&& month2.equals(month1))
//                            {
//                                healthtttl+= data.getAmount();
//                                pieamount[k] = healthtttl;
//                                k++;
//                            }
//                            if(item.equals("Personal")&& month2.equals(month1))
//                            {
//                                personalttl+=data.getAmount();
//                                pieamount[k] = personalttl;
//                                k++;
//
//                            }
//                            if(item.equals("Charity") &&month2.equals(month1))
//                            {
//                                chairtyttl+=data.getAmount();
//                                pieamount[k]= chairtyttl;
//                                k++;
//                            }
//
//
//
//
//
//
//                        }
//
//
//                        Pie pie1 = AnyChart.pie();
//                        List<DataEntry> dataEntryList= new ArrayList<>();
//                        for(int m=0;m<items.length;m++)
//                            dataEntryList.add(new ValueDataEntry(items[m],100));
//                        pie1.data(dataEntryList);
//                        anyChartView.setChart(pie1);
//
//
//
//                        totalspent.setText("₹ "+String.valueOf(total));
//                        totaltrnsport.setText("Spent: ₹ "+String.valueOf(trasnporttota));
//                        food.setText("Spent: ₹ "+String.valueOf(foodtota));
//                        apparel.setText("Spent: ₹ "+String.valueOf(apparelttl));
//                        home.setText("Spent: ₹ "+String.valueOf(homettl));
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                //startActivity(new Intent(Analytics.this,Analytics.class));
//                dialog.dismiss();
//
//            }
//        });
//
//
//
//
//
//
//
//
//    }

}