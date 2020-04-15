<template>
    <div class="app-container">
        <div class="page-heading">
            <h2>
                <span v-text="$t('opusWebApp.region.detail.title')">Region</span> {{region.id}}
            </h2>
        </div>

        <el-form
            ref="region"
            label-width="100px"
            :model="region"
        >
           
            <el-form-item :label="$t('opusWebApp.region.name')" prop="name" required>
                <el-col :span="6">
                    {{region.name}}
                </el-col>
            </el-form-item>
            
            <el-form-item :label="$t('opusWebApp.region.country')" prop="country" required>
                <el-col :span="6">
                    {{region.countryId}}
                </el-col>
            </el-form-item>

            <el-form-item>
                
                <!--<router-link v-if="region.id" :to="{name: 'RegionUpdate', params: {regionId: region.id}}">
                    <el-button type="primary">{{ $t('entity.action.edit') }}</el-button>
                </router-link>-->
                <el-button icon="el-icon-edit" @click="edit(region)" type="primary">{{ $t('entity.action.edit') }}</el-button>
                <el-button icon="el-icon-close" @click="previousState()">{{ $t('entity.action.cancel') }}</el-button>

            </el-form-item>
        </el-form>
    </div>
<!--
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="region">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.region.detail.title')">Region</span> {{region.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.region.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{region.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.region.country')">Country</span>
                    </dt>
                    <dd>
                        <div v-if="region.countryId">
                            <router-link :to="{name: 'CountryView', params: {countryId: region.countryId}}">{{region.countryId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="region.id" :to="{name: 'RegionEdit', params: {regionId: region.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
    -->
</template>

<script lang="ts" src="./region-details.component.ts">
</script>
