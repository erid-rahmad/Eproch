<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cCurrency">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.cCurrency.detail.title')">CCurrency</span> {{cCurrency.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.cCurrency.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{cCurrency.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCurrency.symbol')">Symbol</span>
                    </dt>
                    <dd>
                        <span>{{cCurrency.symbol}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCurrency.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{cCurrency.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cCurrency.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{cCurrency.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cCurrency.id" :to="{name: 'CCurrencyEdit', params: {cCurrencyId: cCurrency.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-currency-details.component.ts">
</script>
