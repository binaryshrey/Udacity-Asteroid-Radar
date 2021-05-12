package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DataBaseEntity


data class AsteroidContainer(val asteroids:List<Asteroid>)

fun AsteroidContainer.asDatabaseModel() : Array<DataBaseEntity>{
    return asteroids.map {
        DataBaseEntity(
            it.id,
            it.codename,
            it.closeApproachDate,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous
        )
    }.toTypedArray()
}