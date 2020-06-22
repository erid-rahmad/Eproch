<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cCity">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.cCity.detail.title')">CCity</span> {{cCity.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.cCity.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{cCity.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCity.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{cCity.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCity.country')">Country</span>
                    </dt>
                    <dd>
                        <div v-if="cCity.countryId">
                            <router-link :to="{name: 'CCountryView', params: {cCountryId: cCity.countryId}}">{{cCity.countryId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCity.region')">Region</span>
                    </dt>
                    <dd>
                        <div v-if="cCity.regionId">
                            <router-link :to="{name: 'CRegionView', params: {cRegionId: cCity.regionId}}">{{cCity.regionId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cCity.id" :to="{name: 'CCityEdit', params: {cCityId: cCity.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-city-details.component.ts">
</script>
