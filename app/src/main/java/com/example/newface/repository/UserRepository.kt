package com.example.newface.repository

import androidx.lifecycle.LiveData
import com.example.newface.datasource.RestDataSource
import com.example.newface.model.User
import com.example.newface.model.UserDao
import kotlinx.coroutines.delay
import javax.inject.Inject

interface UserRepository {
    suspend fun getNewUser(): User
    suspend fun deleteUSer(toDelete: User)
    fun getAllUser(): LiveData<List<User>>
}


class UserRepositoryImp @Inject constructor(
    private val dataSource: RestDataSource,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getNewUser(): User {
        delay(5000)
        val name = dataSource.getUserName().results[0].name!!
        val location = dataSource.getUserLocation().results[0].location!!
        val picture = dataSource.getUserPicture().results[0].picture!!
        val user = User(name.first, name.last, location.city, picture.thumbnail)
        userDao.insert(user)
        return user
    }

    override suspend fun deleteUSer(toDelete: User) {
        userDao.delete(toDelete)
    }

    override fun getAllUser(): LiveData<List<User>> = userDao.getAll()
}