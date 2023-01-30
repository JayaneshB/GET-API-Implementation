package com.project.api_implementation.model

data class MovieData(val adult: Boolean, val budget: Long,val overView:String?=null,val genres:List<GenreDetail>)

data class GenreDetail(val id:Int,val name:String)

