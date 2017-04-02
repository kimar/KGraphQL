package com.github.pgutkowski.kql.schema.impl

import com.github.pgutkowski.kql.SyntaxException
import com.github.pgutkowski.kql.TestClasses
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class DefaultSchemaTest : BaseSchemaTest() {

    @Test
    fun testBasicResult(){
        val result = testedSchema.createResult("{film{title}}")
        assertThat(result.errors, nullValue())
        assertThat(result.data!!["film"] as TestClasses.Film, equalTo(testFilm))
    }

    @Test
    fun testBasicResultWithImpliedFields(){
        val result = testedSchema.createResult("{film}")
        assertThat(result.errors, nullValue())
        assertThat(result.data!!["film"] as TestClasses.Film, equalTo(testFilm))
    }

    @Test
    fun testNamedBasicResult(){
        val result = testedSchema.createResult("query Named {film{title}}")
        assertThat(result.errors, nullValue())
        assertThat(result.data!!["film"] as TestClasses.Film, equalTo(testFilm))
    }

    @Test(expected = SyntaxException::class)
    fun testInvalidQueryMissingBracket(){
        testedSchema.createResult("query Named {film{title}")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testBasicInvalidNamedQuery(){
        testedSchema.createResult("InvalidNamedQuery {film{title}}")
    }
}