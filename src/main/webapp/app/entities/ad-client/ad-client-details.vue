<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDClient">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDClient.detail.title')">ADClient</span> {{aDClient.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDClient.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDClient.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDClient.code')">Code</span>
                    </dt>
                    <dd>
                        <span>{{aDClient.code}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDClient.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{aDClient.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDClient.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDClient.active}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDClient.id" :to="{name: 'ADClientEdit', params: {aDClientId: aDClient.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-client-details.component.ts">
</script>
