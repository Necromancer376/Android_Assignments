package com.example.assignment5.presenter

import android.util.Log
import com.example.assignment5.contracts.Contracts
import com.example.assignment5.network.api.ApiGenerator
import com.example.assignment5.network.model.Character
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CharacterPresenter (
    private val view: Contracts.CharacterView,
) : Contracts.Presenter{

    val disposables = CompositeDisposable()
    val characterTask = ApiGenerator.api.getCharacters()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

    override fun getData() {

        characterTask.subscribe(object : Observer<List<Character>> {
            override fun onSubscribe(d: Disposable) {
//                disposables.add(d)
            }

            override fun onNext(list: List<Character>) {
                view.loadDataInList(list)
            }

            override fun onError(e: Throwable) {
                view.onError(e.message)
            }

            override fun onComplete() {
            }
        })
    }

    override fun onStart() {
        view.init()
    }
}