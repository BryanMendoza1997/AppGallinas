package com.example.appgallinas.Adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appgallinas.fragments.DatosPersonales;
import com.example.appgallinas.fragments.ListaPublicacionesCliente;
import com.example.appgallinas.fragments.PublicacionesCliente;

public class PageAdapter extends FragmentStatePagerAdapter {
    private int numbretabs;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numbretabs=behavior;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return  new PublicacionesCliente();
            case 1:
                return  new ListaPublicacionesCliente();
            case 2:
                return  new DatosPersonales();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numbretabs;
    }
}
