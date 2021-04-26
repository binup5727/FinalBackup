package com.example.final_poc;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class favorite_fragment extends Fragment {
    ListView list;
    ArrayList<String> stock_list = new ArrayList<String>();

    DBHelper dbHelper;




    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.nav_favorite, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            System.out.println("user signed in");
            System.out.println(stock_list.size() + " list size");

            dbHelper = new DBHelper(getContext());
            stock_list = dbHelper.getFavArray(user.getEmail());



            writelist(view);
            // User is signed in
        } else {
            Toast.makeText(getContext(), "Sign In to See Favorites", Toast.LENGTH_LONG).show();
            System.out.println();
            System.out.println("user signed out");
            stock_list = null;
            // No user is signed in
        }

        if(stock_list != null) {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentManager fragmentManager = getFragmentManager();
                    Bundle argument = new Bundle();
                    String favsearch = list.getItemAtPosition(position).toString();
                    argument.putString("VALUE1", favsearch);


                    search_fragment search_frag = new search_fragment();
                    search_frag.setArguments(argument);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container
                            , search_frag).commit();


                }
            });

        }


        return view;
    }

    public void writelist(View view){

        System.out.println("stock list below");
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
                stock_list);
        list = (ListView)view.findViewById(R.id.stock_list);
        list.setAdapter(itemsAdapter);
        System.out.println("stock list above");
    }


}
