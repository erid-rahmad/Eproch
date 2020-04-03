<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="currency">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.currency.detail.title')">Currency</span> {{currency.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.currency.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{currency.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.currency.symbol')">Symbol</span>
                    </dt>
                    <dd>
                        <span>{{currency.symbol}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.currency.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{currency.name}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="currency.id" :to="{name: 'CurrencyEdit', params: {currencyId: currency.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./currency-details.component.ts">
</script>
