<template>
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
</template>

<script lang="ts" src="./country-details.component.ts">
</script>
