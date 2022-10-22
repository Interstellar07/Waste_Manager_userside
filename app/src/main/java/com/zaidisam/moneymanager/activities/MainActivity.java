
package com.zaidisam.moneymanager.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.zaidisam.moneymanager.R;
import com.zaidisam.moneymanager.SplashScreen;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

    public class MainActivity extends AppCompatActivity {
    public int x=0;
    private CardView expenscardview,salarycardview,wastecard;
    private DatabaseReference budgetref,dtb;
      private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private String post_key = "";
    private String item = "";
    private String status = "Sent";
    private boolean uploaded = false;
    private String notes= "";
    private String imgurl="";
    private String type = "";
    private int amount =0;
    private Uri imageUri;
        private ImageView imageView;


 private TextView balancetxt,savingtxt ,expensetxt;

    private ProgressDialog loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        balancetxt = findViewById(R.id.salary);
        //savingtxt = findViewById(R.id.saving);
        //expensetxt = findViewById(R.id.expense);
        progressDialog = new ProgressDialog(this);
        //expenscardview = findViewById(R.id.expenseCardView);
        wastecard = findViewById(R.id.WasteCardView);
        recyclerView = findViewById(R.id.recyclerView);
        dtb = FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        wastecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addwaste();
            }
        });
//        salarycardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // addIncome();
//
//            }
//        });
     dtb = FirebaseDatabase.getInstance().getReference().child("allwaste");
        budgetref= FirebaseDatabase.getInstance().getReference().child("Waste Generations").child(mAuth.getCurrentUser().getUid());



//        budgetref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int salary=0,expense=0,savings =0;String typeof;
//                for(DataSnapshot snap: snapshot.getChildren())
//                {
//                    Data data = snap.getValue(Data.class);
//                    typeof = data.getType().toString();
//                    if(typeof.equals("Expense"))
//                        expense+=data.getAmount();
//                   else
//                       salary+=data.getAmount();
//
//
//                   balancetxt.setText("₹ "+ salary);
//                   expensetxt.setText("₹ "+expense);
//                  savingtxt.setText("Balance:       ₹ "+ String.valueOf(salary-expense));
//
//                }
//                balancetxt.setText("₹ "+ salary);
//                expensetxt.setText("₹ "+expense);
//                savingtxt.setText("Balance:       ₹ "+ String.valueOf(salary-expense));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        loader = new ProgressDialog(this);

    }



        private void addwaste() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.input_layout,null);
        myDialog.setView(myView);
        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        final Spinner wastetype= myView.findViewById(R.id.typespinner);
        final Spinner wastenature= myView.findViewById(R.id.naturespinner);
        final EditText location = myView.findViewById(R.id.Location);
        final EditText amount = myView.findViewById(R.id.amount);
        final EditText time = myView.findViewById(R.id.time);
        final Button cancel = myView.findViewById(R.id.cancel);
         imageView = myView.findViewById(R.id.wasteimg);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);

            }
        });




        final Button save = myView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String budgeamount = amount.getText().toString();
               String locationtxt = location.getText().toString();
               String timetxt = time.getText().toString();
                String Item = wastetype.getSelectedItem().toString();
                String Item2 = wastenature.getSelectedItem().toString();
                if(TextUtils.isEmpty(budgeamount))
                {
                    amount.setError("Amount is Required");
                    return;
                }
                else
                {
                    loader.setMessage("Adding Waste Data");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    String id = budgetref.push().getKey();
                    DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    String date = dateformat.format(cal.getTime());

                    SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                    String month_name = month_date.format(cal.getTime());

                     MutableDateTime epoch = new MutableDateTime();
                    epoch.setDate(0);
                    DateTime now = new DateTime();
                    Months months = Months.monthsBetween(epoch,now);

                    Data data = new Data(Item,Item2,timetxt,date,id,locationtxt,Integer.parseInt(budgeamount),status,imgurl,1,0,0,mAuth.getCurrentUser().getUid());

                    budgetref.child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                if(imageUri!=null)
                                {
                                    Uri uri = imageUri;
                                    final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
                                    fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {

                                                    Toast.makeText(MainActivity.this,"Successfully Added",Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                    uploaded = false;
                                                    imgurl = uri.toString();
                                                    Data data = new Data(Item,Item2,timetxt,date,id,locationtxt,Integer.parseInt(budgeamount),status,imgurl,1,0,0,mAuth.getCurrentUser().getUid());
                                                    budgetref.child(id).setValue(data);
                                                    dtb.child(id).setValue(data);

                                                }
                                            });

                                        }
                                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });


                                }

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                            }
                            loader.dismiss();
                        }
                    });



                }
                dialog.dismiss();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });







        dialog.show();


    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

                imageUri = data.getData();
                imageView.setImageURI(imageUri);

            }
        }
        //    private void addIncome() {
//
//        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View myView = inflater.inflate(R.layout.incomelayout,null);
//        myDialog.setView(myView);
//        final AlertDialog dialog = myDialog.create();
//        dialog.setCancelable(false);
//
//        final Spinner itemSpinner= myView.findViewById(R.id.itemsspinner);
//
//
//
//        final EditText note = myView.findViewById(R.id.note);
//
//        final EditText amount = myView.findViewById(R.id.amount);
//        final Button cancel = myView.findViewById(R.id.cancel);
//        final Button save = myView.findViewById(R.id.save);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String budgeamount = amount.getText().toString();
//                String notes = note.getText().toString();
//                String Item = itemSpinner.getSelectedItem().toString();
//                if(TextUtils.isEmpty(budgeamount))
//                {
//                    amount.setError("Amount is Required");
//                    return;
//                }
//                else
//                {
//                    loader.setMessage("Adding Budget Item");
//                    loader.setCanceledOnTouchOutside(false);
//                    loader.show();
//                    String id = budgetref.push().getKey();
//                    DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
//                    Calendar cal = Calendar.getInstance();
//                    String date = dateformat.format(cal.getTime());
//
//                    SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
//                    String month_name = month_date.format(cal.getTime());
//
//                    MutableDateTime epoch = new MutableDateTime();
//                    epoch.setDate(0);
//                    DateTime now = new DateTime();
//                    Months months = Months.monthsBetween(epoch,now);
//
//                    Data data = new Data("Income",Item,date,id,notes,Integer.parseInt(budgeamount),month_name);
//                    budgetref.child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful())
//                            {
//                                Toast.makeText(MainActivity.this,"Successfully Added",Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                            else
//                            {
//                                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
//                            }
//                            loader.dismiss();
//                        }
//                    });
//
//
//
//                }
//                dialog.dismiss();
//
//            }
//        });
//
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//
//    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Data> options = new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(budgetref,Data.class).build();

        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int i, @NonNull Data model) {


                holder.setDate("Date "+ model.getData());
                holder.setNotes("Location: "+ model.getLocation());
                holder.setWastetype("Waste Type: " + model.getWastetype());
                holder.setwastenature("Waste Nature: "+ model.getWastenature());
                if(model.getS1()==1 && model.getS2()==0 && model.getS3()==0)
                    holder.setStatus("Status: Request Sent");
                else if(model.getS1()==1 && model.getS2()==1 && model.getS3()==0)
                holder.setStatus("Status: Request Acknowledged");
                else if(model.getS1()==1 && model.getS2()==1 && model.getS3()==1)
                    holder.setStatus("Status: Disposed");

                holder.setImgurl("https://cdn-icons-png.flaticon.com/128/3299/3299964.png");





//
//                holder.mView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        post_key = getRef(i).getKey();
//                        item=model.getItem();
//                        amount = model.getAmount();
//                        type= model.getType();
//                        notes= model.getNotes();
//                        updateData();
//                    }
//                });






            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrieve_layout,parent,false);

                return new MyViewHolder(view);
            }
        };



        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();


    }
        private void uploadtoFirebase(Uri uri){

        }
        private String getFileExtension(Uri mUri){

            ContentResolver cr = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(mUri));

        }


//    private void updateData() {
//
//
//        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View mView= inflater.inflate(R.layout.update_layout,null);
//        myDialog.setView(mView);
//        final AlertDialog dialog = myDialog.create();
//        final TextView mItem = mView.findViewById(R.id.itemname);
//        final EditText mAmount = mView.findViewById(R.id.amount);
//        final EditText mNotes = mView.findViewById(R.id.note);
//        mItem.setText(item);
//        mNotes.setText(notes);
//        mAmount.setText(String.valueOf(amount));
//        mAmount.setSelection(String.valueOf(amount).length());
//        Button delBut = mView.findViewById(R.id.btndelete);
//        Button btnUpdate = mView.findViewById(R.id.updatebtn);
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                amount = Integer.parseInt(mAmount.getText().toString());
//                notes = mNotes.getText().toString();
//
//
//                DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
//                Calendar cal = Calendar.getInstance();
//                String date = dateformat.format(cal.getTime());
//                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
//                String month_name = month_date.format(cal.getTime());
//                MutableDateTime epoch = new MutableDateTime();
//                epoch.setDate(0);
//                DateTime now = new DateTime();
//                Data data = new Data(type,item,date,post_key,notes,amount,month_name);
//                budgetref.child(post_key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful())
//                        {
//                            Toast.makeText(MainActivity.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                        }
//                        else
//                        {
//                            Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
//                        }
//                        loader.dismiss();
//                    }
//                });
//
//                dialog.dismiss();
//
//
//            }
//        });
//             delBut.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        budgetref.child(post_key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful())
//                {
//                    Toast.makeText(MainActivity.this,"Deleted ",Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
//                }
//                loader.dismiss();
//            }
//        });
//        dialog.dismiss();
//    }
//});
//
//        dialog.show();
//
//    }

    public void Signout(MenuItem item) {
        progressDialog.setMessage("Signing Out");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Handler().postDelayed(() -> {

            Toast.makeText(MainActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();

            progressDialog.dismiss();
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();

        }, 700);

    }



    public void erasedata(MenuItem item) {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View mView= inflater.inflate(R.layout.deleteconfirm,null);
        myDialog.setView(mView);
        final AlertDialog dialog = myDialog.create();
        dialog.show();
        final Button erase = mView.findViewById(R.id.deletealldata);
        final Button cancel = mView.findViewById(R.id.Cancelerase);

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Erasing all data");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();


                new Handler().postDelayed(() -> {


                     System.out.println("HUEHUEHUE");
                    budgetref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this,"All Data Erased",Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                progressDialog.dismiss();

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            loader.dismiss();
                        }
                    });


                }, 700);




            }
        });

  cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          loader.dismiss();
          dialog.dismiss();
      }
  });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public ImageView imageView;
        public TextView notes;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            mView= itemView;
            imageView= itemView.findViewById(R.id.imageview);
            notes = itemView.findViewById(R.id.note);

        }
        public void setWastetype(String itemName)
        {
            TextView item = mView.findViewById(R.id.Rtvwsttype);
            item.setText(itemName);
        }
        public void setwastenature(String itemnature)
        {

            TextView amount= mView.findViewById(R.id.rtvwstnature);
            amount.setText(itemnature);
        }
        public void setDate(String itemDate)
        {
            TextView date = mView.findViewById(R.id.date);
            date.setText(itemDate);
        }
        public void setNotes(String location)
        {
            TextView locationtxt = mView.findViewById(R.id.Username);
            locationtxt.setText(location);
        }
        public void setStatus(String Status)
        {
            TextView statustxt = mView.findViewById(R.id.status);
            statustxt.setText(Status);
        }
        public void setImgurl(String imgurl)
        {
            Picasso.get()
                    .load(imgurl)
                    .into(imageView);
        }


        }


}