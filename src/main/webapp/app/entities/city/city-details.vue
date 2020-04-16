<template>
<div class="app-container">
        <div class="page-heading">
            <h2>
                <span v-text="$t('opusWebApp.city.detail.title')">City</span> {{city.id}}
            </h2>
        </div>
        <el-form
            ref="city"
            label-width="100px"
            :model="city"
        >
            <el-form-item :label="$t('opusWebApp.city.name')" prop="name" required>
                <el-col :span="6">
                    {{city.name}}
                </el-col>
            </el-form-item>
            <el-form-item :label="$t('opusWebApp.city.country')" prop="country" required>
                <el-col :span="6">
                    {{city.countryName}}
                </el-col>
            </el-form-item>
            <el-form-item :label="$t('opusWebApp.city.region')" prop="region" required>
                <el-col :span="6">
                    {{city.regionName}}
                </el-col>
            </el-form-item>
            <el-form-item>

                <!--<router-link v-if="country.id" :to="{name: 'CountryUpdate', params: {countryId: country.id}}">
                    <el-button type="primary">{{ $t('entity.action.edit') }}</el-button>
                </router-link>-->
                <el-button icon="el-icon-edit" @click="edit(city)" type="primary">{{ $t('entity.action.edit') }}</el-button>
                <el-button icon="el-icon-close" @click="previousState()">{{ $t('entity.action.cancel') }}</el-button>
                
            </el-form-item>
        </el-form>
    </div>

    <!--
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="city">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.city.detail.title')">City</span> {{city.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.city.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{city.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.city.country')">Country</span>
                    </dt>
                    <dd>
                        <div v-if="city.countryId">
                            <router-link :to="{name: 'CountryView', params: {countryId: city.countryId}}">{{city.countryId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.city.region')">Region</span>
                    </dt>
                    <dd>
                        <div v-if="city.regionId">
                            <router-link :to="{name: 'RegionView', params: {regionId: city.regionId}}">{{city.regionId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="city.id" :to="{name: 'CityEdit', params: {cityId: city.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
    -->
</template>

<script lang="ts" src="./city-details.component.ts">
</script>
