<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDReferenceList">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDReferenceList.detail.title')">ADReferenceList</span> {{aDReferenceList.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDReferenceList.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDReferenceList.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReferenceList.value')">Value</span>
                    </dt>
                    <dd>
                        <span>{{aDReferenceList.value}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReferenceList.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{aDReferenceList.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReferenceList.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDReferenceList.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReferenceList.adOrganization')">Ad Organization</span>
                    </dt>
                    <dd>
                        <div v-if="aDReferenceList.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDReferenceList.adOrganizationId}}">{{aDReferenceList.adOrganizationId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDReferenceList.adReference')">Ad Reference</span>
                    </dt>
                    <dd>
                        <div v-if="aDReferenceList.adReferenceId">
                            <router-link :to="{name: 'ADReferenceView', params: {aDReferenceId: aDReferenceList.adReferenceId}}">{{aDReferenceList.adReferenceId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDReferenceList.id" :to="{name: 'ADReferenceListEdit', params: {aDReferenceListId: aDReferenceList.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-reference-list-details.component.ts">
</script>
