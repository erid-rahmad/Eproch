<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="location">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.location.detail.title')">Location</span> {{location.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.location.streetAddress')">Street Address</span>
                    </dt>
                    <dd>
                        <span>{{location.streetAddress}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.location.postalCode')">Postal Code</span>
                    </dt>
                    <dd>
                        <span>{{location.postalCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.location.city')">City</span>
                    </dt>
                    <dd>
                        <div v-if="location.cityId">
                            <router-link :to="{name: 'CityView', params: {cityId: location.cityId}}">{{location.cityId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.location.vendor')">Vendor</span>
                    </dt>
                    <dd>
                        <div v-if="location.vendorId">
                            <router-link :to="{name: 'VendorView', params: {vendorId: location.vendorId}}">{{location.vendorId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="location.id" :to="{name: 'LocationEdit', params: {locationId: location.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./location-details.component.ts">
</script>
