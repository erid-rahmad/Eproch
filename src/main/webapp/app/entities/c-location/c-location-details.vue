<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cLocation">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.cLocation.detail.title')">CLocation</span> {{cLocation.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.cLocation.streetAddress')">Street Address</span>
                    </dt>
                    <dd>
                        <span>{{cLocation.streetAddress}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cLocation.postalCode')">Postal Code</span>
                    </dt>
                    <dd>
                        <span>{{cLocation.postalCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cLocation.taxInvoiceAddress')">Tax Invoice Address</span>
                    </dt>
                    <dd>
                        <span>{{cLocation.taxInvoiceAddress}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cLocation.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{cLocation.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cLocation.city')">City</span>
                    </dt>
                    <dd>
                        <div v-if="cLocation.cityId">
                            <router-link :to="{name: 'CCityView', params: {cCityId: cLocation.cityId}}">{{cLocation.cityId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cLocation.id" :to="{name: 'CLocationEdit', params: {cLocationId: cLocation.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-location-details.component.ts">
</script>
