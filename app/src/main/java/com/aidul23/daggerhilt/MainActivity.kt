package com.aidul23.daggerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //field injection
    @Inject
    lateinit var someClass : SomeClass
//    lateinit var someInterfaceClass : SomeInterfaceClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(someClass.doAThing())
        println(someClass.doSomeOtherThing())
//        println(someInterfaceClass.doAInterfaceThing())
    }
}


class SomeClass
@Inject
constructor(
    //constructor injection
    private val someOtherClass : SomeOtherClass
) {
    fun doAThing() : String {
        return "I am doing a thing!"
    }
    fun doSomeOtherThing() : String {
        return someOtherClass.doSomeOtherThing()
    }
}
class SomeOtherClass
@Inject
constructor() {
    fun doSomeOtherThing() : String {
        return "I am doing some other things!"
    }
}


class SomeInterfaceClass
@Inject
constructor(
    private val someInterfaceImpl: SomeInterface
) {
    fun doAInterfaceThing() :String {
        return "Do a Interface thing!"
    }
}

class SomeInterfaceImpl
@Inject
constructor() : SomeInterface {
    override fun getAThing(): String {
        return "Some Interface"
    }

}

interface SomeInterface {
    fun getAThing() : String
}

//solution of injecting interface
//first we have to create a module
//then make a function to provide the interface
@InstallIn(ApplicationComponentManager::class)
@Module
class MyModule {
    @Singleton
    @Provides
    fun provideSomeInterface():SomeInterface {
        return SomeInterfaceImpl()
    }
}
