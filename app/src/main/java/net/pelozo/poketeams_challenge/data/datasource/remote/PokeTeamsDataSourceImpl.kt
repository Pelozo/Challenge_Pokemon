package net.pelozo.poketeams_challenge.data.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import net.pelozo.domain.Region
import net.pelozo.domain.Team
import net.pelozo.domain.datasource.PokeTeamsDataSource
import net.pelozo.domain.responses.PlayerInfo
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PokeTeamsDataSourceImpl @Inject constructor(
) : PokeTeamsDataSource {

    companion object {
        private const val USER_INFO_PATH = "/users"
    }

    override suspend fun getPlayerInfo(): Flow<Result<PlayerInfo>> = callbackFlow {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val valueListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failure(Exception("Cancelled")))
                close()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val info = dataSnapshot.getValue(PlayerInfo::class.java)
                    trySend(Result.success(info!!))
                } catch (e: Exception) {
                    trySend(Result.failure(e))
                }
            }
        }

        database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}")
            .addValueEventListener(valueListener)

        awaitClose {
            database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}")
                .removeEventListener(valueListener)
        }
    }

    override suspend fun addTeam(team: Team) = suspendCoroutine<Result<String?>> { continuation ->
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var key: String? = null
        if (team.id == null) {
            key =
                database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}/teams/${team.type}")
                    .push().key

            runBlocking {
                val teamCounter =
                    database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}/counter")
                        .get().await()
                val newCount =
                    if (teamCounter.value == null) 1 else teamCounter.getValue(Int::class.java)
                        ?.plus(1)
                team.number = newCount
                database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}/counter")
                    .setValue(newCount)
            }
        } else {
            key = team.id
        }

        key?.let { safeKey ->
            database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}/teams/${team.type}/$key")
                .setValue(team)
                .addOnSuccessListener {
                    continuation.resume(Result.success(safeKey))
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(Exception("Couldn't create team")))
                }
        } ?: run {
            continuation.resume(Result.failure(Exception("Couldn't create key")))
        }
    }


    override suspend fun getTeamsByRegion(region: Region): Flow<Result<List<Team>>> = callbackFlow {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val valueListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failure(Exception("Cancelled")))
                close()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val teams = mutableListOf<Team>()
                    dataSnapshot.children.forEach {
                        val team = it.getValue(Team::class.java)
                        team?.id = it.key
                        team?.let {
                            teams.add(it)
                        }
                    }
                    trySend(Result.success(teams))
                } catch (e: Exception) {
                    trySend(Result.failure(e))
                }
            }
        }
        database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}/teams/${region.name}")
            .addValueEventListener(valueListener)

        awaitClose {
            database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}")
                .removeEventListener(valueListener)
        }
    }

    override suspend fun removeTeam(team: Team) =
        suspendCoroutine<Result<String?>> { continuation ->
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}/teams/${team.type}/${team.id}")
                .removeValue()
                .addOnSuccessListener {
                    continuation.resume(Result.success(team.id))
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(Exception("Couldn't create team")))
                }

        }

    override suspend fun getAllTeams(): Flow<Result<List<Team>>> = callbackFlow {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val valueListener = object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                trySend(Result.failure(Exception("Cancelled")))
                close()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val teams = mutableListOf<Team>()

                    dataSnapshot.children.forEach {
                        it.children.forEach {
                            val team = it.getValue(Team::class.java)
                            team?.id = it.key
                            team?.let {
                                teams.add(it)
                            }
                        }
                    }
                    trySend(Result.success(teams))
                } catch (e: Exception) {
                    trySend(Result.failure(e))
                }
            }
        }
        database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}/teams/")
            .addValueEventListener(valueListener)

        awaitClose {
            database.getReference("/$USER_INFO_PATH/${FirebaseAuth.getInstance().currentUser?.uid}")
                .removeEventListener(valueListener)
        }
    }
}
