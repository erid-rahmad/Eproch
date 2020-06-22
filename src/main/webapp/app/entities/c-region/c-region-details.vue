<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cRegion">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.cRegion.detail.title')">CRegion</span> {{cRegion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.cRegion.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{cRegion.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cRegion.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{cRegion.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cRegion.country')">Country</span>
                    </dt>
                    <dd>
                        <div v-if="cRegion.countryId">
                            <router-link :to="{name: 'CCountryView', params: {cCountryId: cRegion.countryId}}">{{cRegion.countryId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cRegion.id" :to="{name: 'CRegionEdit', params: {cRegionId: cRegion.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-region-details.component.ts">
</script>
