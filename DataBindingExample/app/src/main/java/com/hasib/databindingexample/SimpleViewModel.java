package com.hasib.databindingexample;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class SimpleViewModel extends ViewModel {
    private final MutableLiveData<String> _name = new MutableLiveData<>("Shahrin");
    private final MutableLiveData<String> _lastName = new MutableLiveData<>("Sultana");
    private final MutableLiveData<Integer> _likes = new MutableLiveData<>(0);

    LiveData<String> name = _name;
    LiveData<String> lastName = _lastName;
    LiveData<Integer> likes = _likes;
    LiveData<Popularity> popularity = Transformations.map(_likes, new Function<Integer, Popularity>() {
        @Override
        public Popularity apply(Integer input) {
            return (input > 9) ? Popularity.STAR : (input > 4) ? Popularity.POPULAR : Popularity.NORMAL;
        }
    });

    public void onLike() {
        _likes.setValue(_likes.getValue() + 1);
    }

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getLastName() {
        return lastName;
    }

    public LiveData<Integer> getLikes() {
        return likes;
    }

    public LiveData<Popularity> getPopularity() {
        return popularity;
    }

    public enum Popularity {
        NORMAL, POPULAR, STAR
    }
}
