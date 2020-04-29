<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDTable">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDTable.detail.title')">ADTable</span> {{aDTable.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDTable.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDTable.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTable.view')">View</span>
                    </dt>
                    <dd>
                        <span>{{aDTable.view}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTable.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDTable.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTable.adClient')">Ad Client</span>
                    </dt>
                    <dd>
                        <div v-if="aDTable.adClientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDTable.adClientId}}">{{aDTable.adClientId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTable.adOrganization')">Ad Organization</span>
                    </dt>
                    <dd>
                        <div v-if="aDTable.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDTable.adOrganizationId}}">{{aDTable.adOrganizationId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDTable.id" :to="{name: 'ADTableEdit', params: {aDTableId: aDTable.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-table-details.component.ts">
</script>
