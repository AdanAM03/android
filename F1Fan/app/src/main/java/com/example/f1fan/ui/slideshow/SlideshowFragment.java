package com.example.f1fan.ui.slideshow;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.example.f1fan.databinding.FragmentSlideshowBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    private TabLayout tabs;
    private ViewPager2 viewPager2;
    private PagerAdapter2 pagerAdapter2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
//        getActivity().getActionBar().setTitle("Equipos y pilotos");
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // PARA EL ARRASTRE DE DEDO
        viewPager2 = binding.viewPager2;
        pagerAdapter2 = new PagerAdapter2(this.getActivity());
        viewPager2.setAdapter(pagerAdapter2);

        // PARA LAS PESTAÑAS
        tabs = binding.tabs;
        new TabLayoutMediator(tabs,viewPager2,(tab,position)->{
            if (position==0)tab.setText("Equipos");
            if (position==1)tab.setText("Pilotos");
        }).attach();
        //final TextView textView = binding.textSlideshow;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}