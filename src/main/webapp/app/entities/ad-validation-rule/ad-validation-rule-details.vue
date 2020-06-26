<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="adValidationRule">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.adValidationRule.detail.title')">AdValidationRule</span> {{adValidationRule.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.adValidationRule.uid')">Uid</span>
                    </dt>
                    <dd>
                        <span>{{adValidationRule.uid}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.adValidationRule.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{adValidationRule.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.adValidationRule.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{adValidationRule.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.adValidationRule.query')">Query</span>
                    </dt>
                    <dd>
                        <span>{{adValidationRule.query}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.adValidationRule.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{adValidationRule.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.adValidationRule.adOrganization')">Ad Organization</span>
                    </dt>
                    <dd>
                        <div v-if="adValidationRule.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: adValidationRule.adOrganizationId}}">{{adValidationRule.adOrganizationId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="adValidationRule.id" :to="{name: 'AdValidationRuleEdit', params: {adValidationRuleId: adValidationRule.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-validation-rule-details.component.ts">
</script>
