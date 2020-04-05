<template>
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
</template>

<script lang="ts" src="./region-details.component.ts">
</script>
