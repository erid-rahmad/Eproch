<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDWindow">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDWindow.detail.title')">ADWindow</span> {{aDWindow.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDWindow.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDWindow.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDWindow.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{aDWindow.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDWindow.titleLogic')">Title Logic</span>
                    </dt>
                    <dd>
                        <span>{{aDWindow.titleLogic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDWindow.type')">Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('opusWebApp.ADWindowType.' + aDWindow.type)">{{aDWindow.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDWindow.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDWindow.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDWindow.adOrganization')">Ad Organization</span>
                    </dt>
                    <dd>
                        <div v-if="aDWindow.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDWindow.adOrganizationId}}">{{aDWindow.adOrganizationId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDWindow.id" :to="{name: 'ADWindowEdit', params: {aDWindowId: aDWindow.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-window-details.component.ts">
</script>
