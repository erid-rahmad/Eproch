<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDOrganization">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDOrganization.detail.title')">ADOrganization</span> {{aDOrganization.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDOrganization.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDOrganization.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDOrganization.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{aDOrganization.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDOrganization.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{aDOrganization.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDOrganization.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDOrganization.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDOrganization.client')">Client</span>
                    </dt>
                    <dd>
                        <div v-if="aDOrganization.clientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDOrganization.clientId}}">{{aDOrganization.clientId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDOrganization.id" :to="{name: 'ADOrganizationEdit', params: {aDOrganizationId: aDOrganization.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-organization-details.component.ts">
</script>
