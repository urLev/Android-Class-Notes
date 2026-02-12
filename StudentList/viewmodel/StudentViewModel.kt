package com.goble.studentlist.viewmodel

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goble.studentlist.data.Student
import com.goble.studentlist.data.StudentRepository
import kotlinx.coroutines.launch

class StudentViewModel: ViewModel() {

    val studentRepo: StudentRepository = StudentRepository()
    private val _studentData = MutableLiveData<Student>()
    val studentData: LiveData<Student> = _studentData
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStudentData() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            val studentResult = studentRepo.fetchStudentData()
            _studentData.postValue(studentResult)
            _isLoading.postValue(false)
        }
    }

}