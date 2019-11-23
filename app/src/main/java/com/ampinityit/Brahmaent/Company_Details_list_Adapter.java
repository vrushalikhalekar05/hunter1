package com.ampinityit.Brahmaent;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class Company_Details_list_Adapter  extends RecyclerView.Adapter<Company_Details_list_Adapter.MyviewHolder> {
     ArrayList<Vehicledetail> vehicledetailArrayList;Context context;


    public Company_Details_list_Adapter(ArrayList<Vehicledetail> vehicledetail, Context context) {
        this.vehicledetailArrayList = vehicledetail;
        this.context = context;
    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_company_details_list_adapter, parent, false);
        MyviewHolder myviewHolder = new MyviewHolder(view);
        return myviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        final Vehicledetail vehicledetail = vehicledetailArrayList.get(position);
        holder.companyname.setText(vehicledetail.getRegistrationNo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,InfomationActivity.class);
                intent.putExtra("BRANCHNAME",vehicledetail.getBRANCHNAME());
                intent.putExtra("TEAMLEADERNAME",vehicledetail.getTEAMLEADERNAME());
                intent.putExtra("TLMOBNO",vehicledetail.getTLMOBNO());
                intent.putExtra("AccountNo",vehicledetail.getAccountNo());
                intent.putExtra("Allocation_MAR_APR19",vehicledetail.getAllocation_MAR_APR19());
                intent.putExtra("VehicleMake",vehicledetail.getVehicleMake());
                intent.putExtra("VehicleModel",vehicledetail.getVehicleModel());
                intent.putExtra("ChassisNo",vehicledetail.getChassisNo());
                intent.putExtra("EngineNo",vehicledetail.getEngineNo());
                intent.putExtra("POSINCR",vehicledetail.getPOSINCR());
                intent.putExtra("MobileNo",vehicledetail.getMobileNo());
                intent.putExtra("CustomerAddress",vehicledetail.getCustomerAddress());
                intent.putExtra("CustomerFullName",vehicledetail.getCustomerFullName());
                intent.putExtra("RegistrationNo",vehicledetail.getRegistrationNo());

                intent.putExtra("BUSREGION",vehicledetail.getBUSREGION());
                intent.putExtra("ManufacturingYear",vehicledetail.getManufacturingYear());
                intent.putExtra("CollectionDPD",vehicledetail.getCollectionDPD());
                intent.putExtra("ComapnyName",vehicledetail.getComapnyName());
                intent.putExtra("PhoneNo",vehicledetail.getPhoneNo());
                intent.putExtra("RegistrationNo",vehicledetail.getRegistrationNo());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicledetailArrayList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private TextView companyname;

        public MyviewHolder(View itemView) {
            super(itemView);
            companyname = itemView.findViewById(R.id.company_name);
        }
    }
}
