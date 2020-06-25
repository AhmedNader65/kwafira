package com.almusand.kawfira.ui.main.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almusand.kawfira.Bases.BaseFragment;
import com.almusand.kawfira.Models.categories.CategoriesModel;
import com.almusand.kawfira.Models.categories.ServicesModel;
import com.almusand.kawfira.Models.offers.SliderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.FragmentHomeBinding;
import com.almusand.kawfira.ui.allServices.ServicesActivity;
import com.almusand.kawfira.ui.main.HomeActivity;
import com.almusand.kawfira.ui.main.ui.home.adapter.CategoriesAdapter;
import com.almusand.kawfira.ui.main.ui.home.adapter.ServicesAdapter;
import com.almusand.kawfira.ui.main.ui.home.adapter.SliderAdapter;
import com.almusand.kawfira.ui.map.MapActivity;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.google.gson.Gson;

import java.util.List;


public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewModel>  implements ServicesAdapter.onCartListener,HomeNavigator{
    List<ServicesModel> servicesModels;
    List<CategoriesModel> categoriesModels;
    HomeViewModel homeViewModel;
    GlobalPreferences pf;
    FragmentHomeBinding binding;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeViewModel getViewModel() {
        homeViewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel.class);
        return homeViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        homeViewModel.setNavigator(this);
        pf = new GlobalPreferences(getContext());
        homeViewModel.getOffers().observe(this, offerListUpdateObserver);
        homeViewModel.getCategories().observe(this, categoriesListUpdateObserver);
        homeViewModel.getServices().observe(this, servicesListUpdateObserver);
        homeViewModel.initOffers(pf.getUserAuth());
        homeViewModel.initCategories();
        homeViewModel.initServices();

    }


    Observer<List<SliderModel>> offerListUpdateObserver = offersList -> {
        SliderAdapter adapter = new SliderAdapter();

        binding.viewPager.setAdapter(adapter);
        adapter.renewItems(offersList);
        SliderModel model =new SliderModel();
        binding.emptyOffers.setVisibility(View.GONE);
        model.setId(2);
        model.setActive(1);
        model.setCoupon("432432");
        model.setDescription_ar("هاي هاي هاي");
        model.setDescription_en("fdsfds");
        model.setTitle_ar("العرض رقم ١");
        model.setTitle_en("offer num 1");
        model.setImage("https://en.es-static.us/upl/2018/12/comet-wirtanen-Jack-Fusco-dec-2018-Anza-Borrego-desert-CA-e1544613895713.jpg");
        adapter.addItem(model);
        model =new SliderModel();
        model.setId(3);
        model.setActive(1);
        model.setCoupon("432432");
        model.setDescription_ar("هاي هاي هاي");
        model.setDescription_en("fdsfds");
        model.setTitle_ar("العرض رقم 2");
        model.setTitle_en("offer num 2");
        model.setImage("https://filedn.com/ltOdFv1aqz1YIFhf4gTY8D7/ingus-info/BLOGS/Photography-stocks3/stock-photography-slider.jpg");
        adapter.addItem(model);
        model =new SliderModel();
        model.setId(5);
        model.setActive(1);
        model.setCoupon("432432");
        model.setDescription_ar("هاي هاي هاي");
        model.setDescription_en("fdsfds");
        model.setTitle_ar("العرض رقم 3");
        model.setTitle_en("offer num 3");
        model.setImage("https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg");
        adapter.addItem(model);
        Log.e("Here","changed");
    };
    Observer<List<CategoriesModel>> categoriesListUpdateObserver = categoriesList -> {
        categoriesModels = categoriesList;
        Log.e("changed","categories");
        final CategoriesAdapter adapter = new CategoriesAdapter();
        binding.categories.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        binding.emptyCategories.setVisibility(View.GONE);

        binding.categories.setAdapter(adapter);
        adapter.renewItems(categoriesList);

        CategoriesModel model =new CategoriesModel();
        model.setId(2);
        model.setActive(true);
        model.setName_ar("العرض رقم ١");
        model.setName_en("offer num 1");
        model.setImage("https://en.es-static.us/upl/2018/12/comet-wirtanen-Jack-Fusco-dec-2018-Anza-Borrego-desert-CA-e1544613895713.jpg");
        adapter.addItem(model);
        model =new CategoriesModel();
        model.setId(3);
        model.setActive(true);
        model.setName_ar("العرض رقم 2");
        model.setName_en("offer num 2");
        model.setImage("https://filedn.com/ltOdFv1aqz1YIFhf4gTY8D7/ingus-info/BLOGS/Photography-stocks3/stock-photography-slider.jpg");
        adapter.addItem(model);
        model =new CategoriesModel();
        model.setId(5);
        model.setActive(true);
        model.setName_ar("العرض رقم 3");
        model.setName_en("offer num 3");
        model.setImage("https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg");
        adapter.addItem(model);

    };
    private ServicesAdapter servicesAdapter;
    Observer<List<ServicesModel>> servicesListUpdateObserver = servicesList -> {
        servicesModels = servicesList;
         servicesAdapter = new ServicesAdapter(this);
        binding.services.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
        binding.services.setAdapter(servicesAdapter);
        servicesAdapter.renewItems(servicesList);

        binding.emptyServices.setVisibility(View.GONE);
        ServicesModel model2 =new ServicesModel();
        model2.setId(5);
        model2.setActive(true);
        model2.setTitle_ar("الخدمة رقم 3");
        model2.setInitial_price(1400);
        model2.setTitle_en("offer num 3");
        model2.setImage("https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg");
        if(model2==null){
            Log.e("model2","null");
        }
        if(servicesAdapter==null){
            Log.e("servicesAdapter","null");
        }
        servicesAdapter.addItem(model2);
        model2 =new ServicesModel();
        model2.setId(5);
        model2.setActive(true);
        model2.setTitle_ar("الخدمة رقم 4");
        model2.setTitle_en("offer num 3");
        model2.setInitial_price(10);
        model2.setImage("https://filedn.com/ltOdFv1aqz1YIFhf4gTY8D7/ingus-info/BLOGS/Photography-stocks3/stock-photography-slider.jpg");
        servicesAdapter.addItem(model2);
        model2 =new ServicesModel();
        model2.setId(5);
        model2.setActive(true);
        model2.setTitle_ar("الخدمة رقم ٥");
        model2.setTitle_en("offer num 3");
        model2.setInitial_price(30);
        model2.setImage("https://en.es-static.us/upl/2018/12/comet-wirtanen-Jack-Fusco-dec-2018-Anza-Borrego-desert-CA-e1544613895713.jpg");
        servicesAdapter.addItem(model2);
    };

    @Override
    public void showCart() {

        binding.cart.setVisibility(View.VISIBLE);
        binding.cartPrice.setText(pf.getCost()+" ريال");
        binding.count.setText(pf.getCartCounter()+"");
    }

    @Override
    public void hideCart() {
        binding.cart.setVisibility(View.GONE);

    }

    public void refreshCart() {
        Log.e("Log","Refreshing");
        servicesAdapter.refreshCart();
    }

    @Override
    public void onShowAllServices() {
        String listSerializedToJson = new Gson().toJson(servicesModels);
        Intent intent = ServicesActivity.newIntent(getContext());
        intent.putExtra("type", "allServices");
        intent.putExtra("services", listSerializedToJson);

        (getActivity()).startActivityForResult(intent, HomeActivity.OPENALLSERVICE_CODE);
    }

    @Override
    public void onShowAllCategories() {
        String listSerializedToJson = new Gson().toJson(categoriesModels);
        Intent intent = ServicesActivity.newIntent(getContext());
        intent.putExtra("type", "allCategories");
        intent.putExtra("categories", listSerializedToJson);

        (getActivity()).startActivityForResult(intent, HomeActivity.OPENALLSERVICE_CODE);
    }

    @Override
    public void openMapsActivity() {
        Intent intent = MapActivity.newIntent(getContext());
            startActivity(intent);
    }
}