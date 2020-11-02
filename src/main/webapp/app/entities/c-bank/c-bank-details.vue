<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cBank">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.cBank.detail.title')">CBank</span> {{cBank.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.cBank.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{cBank.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cBank.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{cBank.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cBank.shortName')">Short Name</span>
                    </dt>
                    <dd>
                        <span>{{cBank.shortName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cBank.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{cBank.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cBank.swiftCode')">Swift Code</span>
                    </dt>
                    <dd>
                        <span>{{cBank.swiftCode}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.cBank.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{cBank.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cBank.id" :to="{name: 'CBankEdit', params: {cBankId: cBank.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./c-bank-details.component.ts">
</script>
