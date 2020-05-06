<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDColumn">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDColumn.detail.title')">ADColumn</span> {{aDColumn.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.sqlName')">Sql Name</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.sqlName}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.fieldLength')">Field Length</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.fieldLength}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.key')">Key</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.key}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.type')">Type</span>
                    </dt>
                    <dd>
                        <span v-text="$t('opusWebApp.ADColumnType.' + aDColumn.type)">{{aDColumn.type}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.mandatory')">Mandatory</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.mandatory}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.mandatoryLogic')">Mandatory Logic</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.mandatoryLogic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.readOnlyLogic')">Read Only Logic</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.readOnlyLogic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.updatable')">Updatable</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.updatable}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.defaultValue')">Default Value</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.defaultValue}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.formatPattern')">Format Pattern</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.formatPattern}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.minLength')">Min Length</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.minLength}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.maxLength')">Max Length</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.maxLength}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.minValue')">Min Value</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.minValue}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.maxValue')">Max Value</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.maxValue}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDColumn.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.adClient')">Ad Client</span>
                    </dt>
                    <dd>
                        <div v-if="aDColumn.adClientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDColumn.adClientId}}">{{aDColumn.adClientId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.adOrganization')">Ad Organization</span>
                    </dt>
                    <dd>
                        <div v-if="aDColumn.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDColumn.adOrganizationId}}">{{aDColumn.adOrganizationId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.adReference')">Ad Reference</span>
                    </dt>
                    <dd>
                        <div v-if="aDColumn.adReferenceId">
                            <router-link :to="{name: 'ADReferenceView', params: {aDReferenceId: aDColumn.adReferenceId}}">{{aDColumn.adReferenceId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDColumn.adTable')">Ad Table</span>
                    </dt>
                    <dd>
                        <div v-if="aDColumn.adTableId">
                            <router-link :to="{name: 'ADTableView', params: {aDTableId: aDColumn.adTableId}}">{{aDColumn.adTableId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDColumn.id" :to="{name: 'ADColumnEdit', params: {aDColumnId: aDColumn.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-column-details.component.ts">
</script>
