<template>
    <div class="app-container">
        <div class="page-heading">
            <h2>
                <span v-text="$t('opusWebApp.country.detail.title')">Country</span> {{country.id}}
            </h2>
        </div>
        <el-form
            ref="country"
            label-width="100px"
            :model="country"
        >
            <el-form-item :label="$t('opusWebApp.country.name')" prop="name" required>
                <el-col :span="6">
                    {{country.name}}
                </el-col>
            </el-form-item>
            <el-form-item :label="$t('opusWebApp.country.code')" prop="code" required>
                <el-col :span="6">
                    {{country.code}}
                </el-col>
            </el-form-item>
            <el-form-item :label="$t('opusWebApp.country.currency')" prop="symbol" required>
                <el-col :span="6">
                    {{country.currencyCode}}
                </el-col>
            </el-form-item>
            <el-form-item>

                <!--<router-link v-if="country.id" :to="{name: 'CountryUpdate', params: {countryId: country.id}}">
                    <el-button type="primary">{{ $t('entity.action.edit') }}</el-button>
                </router-link>-->
                <el-button icon="el-icon-edit" @click="edit(country)" type="primary">{{ $t('entity.action.edit') }}</el-button>
                <el-button icon="el-icon-close" @click="previousState()">{{ $t('entity.action.cancel') }}</el-button>
                
            </el-form-item>
        </el-form>
    </div>
<!--
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="country">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.country.detail.title')">Country</span> {{country.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.country.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{country.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.country.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{country.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.country.currency')">Currency</span>
                    </dt>
                    <dd>
                        <div v-if="country.currencyId">
                            <router-link :to="{name: 'CountryDetails', params: {currencyId: country.currencyId}}">{{country.currencyId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="country.id" :to="{name: 'CountryUpdate', params: {countryId: country.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
    -->
</template>

<script lang="ts" src="./country-details.component.ts">
</script>
