package com.example.moneyshare.domain.util

interface DomainMapper<DomainModel, T> {
    fun mapToDomainModel(model: T) : DomainModel

    fun mapFromDomainModel(domainModel: DomainModel) : T

    fun mapToDomainModelList(models: List<T>) : List<DomainModel> {
        return models.map { mapToDomainModel(it) }
    }

    fun mapFromDomainModelList(domainModels: List<DomainModel>) : List<T> {
        return domainModels.map { mapFromDomainModel(it) }
    }
}