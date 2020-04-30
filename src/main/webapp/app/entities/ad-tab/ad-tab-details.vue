<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDTab">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDTab.detail.title')">ADTab</span> {{aDTab.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.targetEndpoint')">Target Endpoint</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.targetEndpoint}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.writable')">Writable</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.writable}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.displayLogic')">Display Logic</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.displayLogic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.readOnlyLogic')">Read Only Logic</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.readOnlyLogic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.filterQuery')">Filter Query</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.filterQuery}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.orderQuery')">Order Query</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.orderQuery}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDTab.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.adClient')">Ad Client</span>
                    </dt>
                    <dd>
                        <div v-if="aDTab.adClientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDTab.adClientId}}">{{aDTab.adClientId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.adOrganization')">Ad Organization</span>
                    </dt>
                    <dd>
                        <div v-if="aDTab.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDTab.adOrganizationId}}">{{aDTab.adOrganizationId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.adTable')">Ad Table</span>
                    </dt>
                    <dd>
                        <div v-if="aDTab.adTableId">
                            <router-link :to="{name: 'ADTableView', params: {aDTableId: aDTab.adTableId}}">{{aDTab.adTableId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.adWindow')">Ad Window</span>
                    </dt>
                    <dd>
                        <div v-if="aDTab.adWindowId">
                            <router-link :to="{name: 'ADWindowView', params: {aDWindowId: aDTab.adWindowId}}">{{aDTab.adWindowId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDTab.parentTab')">Parent Tab</span>
                    </dt>
                    <dd>
                        <div v-if="aDTab.parentTabId">
                            <router-link :to="{name: 'ADTabView', params: {aDTabId: aDTab.parentTabId}}">{{aDTab.parentTabId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDTab.id" :to="{name: 'ADTabEdit', params: {aDTabId: aDTab.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-tab-details.component.ts">
</script>
