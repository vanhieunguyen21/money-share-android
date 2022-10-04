package com.example.moneyshare.domain.data_source.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "member",
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"]
        )
    ]
)
data class MemberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String? = null,
    // Foreign key to group entity
    val groupId: Int? = null
)
