package com.almusand.kawfira.ui.main.ui.editingActivities.lang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.almusand.kawfira.Bases.BaseActivity;
import com.almusand.kawfira.R;
import com.almusand.kawfira.databinding.ActivityChangeLanguageBinding;
import com.almusand.kawfira.utils.CommonUtils;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.github.islamkhsh.BR;
import com.jakewharton.processphoenix.ProcessPhoenix;

public class ChangeLanguageActivity extends BaseActivity<ActivityChangeLanguageBinding,LanguageVM>  implements  LanguageNavigator{
    ActivityChangeLanguageBinding binding;
    LanguageVM vm;
GlobalPreferences gp;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_language;
    }

    @Override
    public LanguageVM getViewModel() {
        vm = ViewModelProviders.of(this).get(LanguageVM.class);
        return vm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        gp = new GlobalPreferences(this);
        vm.setNavigator(this);
        vm.getChecked(gp.getLanguage());
        binding.radioLanguage.setOnCheckedChangeListener((group, checkedId) -> {
            CommonUtils.setLocale(this,checkedId==R.id.ar? "ar": "en");
            gp.storeLanguage(checkedId==R.id.ar? "ar": "en");
            ProcessPhoenix.triggerRebirth(this);

        });
    }

    @Override
    public void checkAr() {
        binding.ar.setChecked(true);
    }

    @Override
    public void checkEng() {
        binding.en.setChecked(true);
    }
}