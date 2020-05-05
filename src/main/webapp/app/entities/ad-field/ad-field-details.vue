<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="aDField">
                <h2 class="jh-entity-heading"><span v-text="$t('opusWebApp.aDField.detail.title')">ADField</span> {{aDField.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{aDField.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.description')">Description</span>
                    </dt>
                    <dd>
                        <span>{{aDField.description}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.hint')">Hint</span>
                    </dt>
                    <dd>
                        <span>{{aDField.hint}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.staticText')">Static Text</span>
                    </dt>
                    <dd>
                        <span>{{aDField.staticText}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.staticField')">Static Field</span>
                    </dt>
                    <dd>
                        <span>{{aDField.staticField}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.labelOnly')">Label Only</span>
                    </dt>
                    <dd>
                        <span>{{aDField.labelOnly}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.showLabel')">Show Label</span>
                    </dt>
                    <dd>
                        <span>{{aDField.showLabel}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.showInGrid')">Show In Grid</span>
                    </dt>
                    <dd>
                        <span>{{aDField.showInGrid}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.showInDetail')">Show In Detail</span>
                    </dt>
                    <dd>
                        <span>{{aDField.showInDetail}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.gridSequence')">Grid Sequence</span>
                    </dt>
                    <dd>
                        <span>{{aDField.gridSequence}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.detailSequence')">Detail Sequence</span>
                    </dt>
                    <dd>
                        <span>{{aDField.detailSequence}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.displayLogic')">Display Logic</span>
                    </dt>
                    <dd>
                        <span>{{aDField.displayLogic}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.writable')">Writable</span>
                    </dt>
                    <dd>
                        <span>{{aDField.writable}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.columnNo')">Column No</span>
                    </dt>
                    <dd>
                        <span>{{aDField.columnNo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.columnSpan')">Column Span</span>
                    </dt>
                    <dd>
                        <span>{{aDField.columnSpan}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.active')">Active</span>
                    </dt>
                    <dd>
                        <span>{{aDField.active}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.adClient')">Ad Client</span>
                    </dt>
                    <dd>
                        <div v-if="aDField.adClientId">
                            <router-link :to="{name: 'ADClientView', params: {aDClientId: aDField.adClientId}}">{{aDField.adClientId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.adOrganization')">Ad Organization</span>
                    </dt>
                    <dd>
                        <div v-if="aDField.adOrganizationId">
                            <router-link :to="{name: 'ADOrganizationView', params: {aDOrganizationId: aDField.adOrganizationId}}">{{aDField.adOrganizationId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.adReference')">Ad Reference</span>
                    </dt>
                    <dd>
                        <div v-if="aDField.adReferenceId">
                            <router-link :to="{name: 'ADReferenceView', params: {aDReferenceId: aDField.adReferenceId}}">{{aDField.adReferenceId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('opusWebApp.aDField.adTab')">Ad Tab</span>
                    </dt>
                    <dd>
                        <div v-if="aDField.adTabId">
                            <router-link :to="{name: 'ADTabView', params: {aDTabId: aDField.adTabId}}">{{aDField.adTabId}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="aDField.id" :to="{name: 'ADFieldEdit', params: {aDFieldId: aDField.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ad-field-details.component.ts">
</script>
