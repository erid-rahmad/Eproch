<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDReference">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDReference.detail.title')">ADReference</span> {{aDReference.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDReference.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDReference.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReference.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{aDReference.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReference.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{aDReference.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReference.referenceType')">Reference Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('opusWebApp.ADReferenceType.' + aDReference.referenceType)">{{aDReference.referenceType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReference.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDReference.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDReference.id" :to="{name: 'ADReferenceEdit', params: {aDReferenceId: aDReference.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-reference-details.component.ts">
</script>
