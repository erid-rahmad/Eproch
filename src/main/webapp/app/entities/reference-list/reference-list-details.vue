<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="referenceList">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.referenceList.detail.title')">ReferenceList</span> {{referenceList.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.referenceList.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{referenceList.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.referenceList.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{referenceList.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.referenceList.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{referenceList.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.referenceList.reference')">Reference</span>
                    </dt>
                    <dd>
                        <div v-if="referenceList.referenceId">
                            <router-link :to="{name: 'ReferenceView', params: {referenceId: referenceList.referenceId}}">{{referenceList.referenceId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="referenceList.id" :to="{name: 'ReferenceListEdit', params: {referenceListId: referenceList.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./reference-list-details.component.ts">
</script>
