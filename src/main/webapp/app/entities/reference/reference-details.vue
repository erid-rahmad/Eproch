<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="reference">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.reference.detail.title')">Reference</span> {{reference.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.reference.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{reference.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.reference.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{reference.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.reference.referenceType')">Reference Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('opusWebApp.ReferenceType.' + reference.referenceType)">{{reference.referenceType}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="reference.id" :to="{name: 'ReferenceEdit', params: {referenceId: reference.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./reference-details.component.ts">
</script>
