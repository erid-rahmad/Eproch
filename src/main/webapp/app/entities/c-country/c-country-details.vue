<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cCountry">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.cCountry.detail.title')">CCountry</span> {{cCountry.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.cCountry.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{cCountry.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCountry.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{cCountry.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCountry.withRegion')">With Region</span>
                    </dt>
                    <dd>
                        <span>{{cCountry.withRegion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCountry.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{cCountry.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCountry.currency')">Currency</span>
                    </dt>
                    <dd>
                        <div v-if="cCountry.currencyId">
                            <router-link :to="{name: 'CCurrencyView', params: {cCurrencyId: cCountry.currencyId}}">{{cCountry.currencyId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cCountry.id" :to="{name: 'CCountryEdit', params: {cCountryId: cCountry.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-country-details.component.ts">
</script>
