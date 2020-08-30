package com.example.appgallinas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.appgallinas.Adaptadores.PageAdapter;
import com.google.android.material.tabs.TabLayout;

public class Cliente extends AppCompatActivity {

    //prueba
    String nombre="Anonimo";
    String id="";

    private MenuItem iten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        //Id y Nombre del usuario que inicio sesion puedes ocupar estar variables para eso
        nombre=getIntent().getStringExtra("Idusuario");
        id =getIntent().getStringExtra("Nombre");
        setToolbar();

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Ofertas").setIcon(R.drawable.ic_publicaciones));
        tabLayout.addTab(tabLayout.newTab().setText("Pedidos").setIcon(R.drawable.ic_pedidos));
        tabLayout.addTab(tabLayout.newTab().setText("Datos").setIcon(R.drawable.ic_datospersonales));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager=(ViewPager) findViewById(R.id.viewPager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0){
                    adapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==1){
                    adapter.notifyDataSetChanged();
                }
                if(tab.getPosition()==2){
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
      //      case android.R.id.home:
                //abrir el menu lateral
        //        drawerLayout.openDrawer(GravityCompat.START);
          //      return true;

            case R.id.action_salir:
              //  Salir de la aplicación.
                Intent in = new Intent(this, Login.class);
                Toast.makeText(this,"Sesión Cerrada",Toast.LENGTH_SHORT).show();
                startActivity(in);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem=menu.findItem(R.id.action_buscar);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Buscar .....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
