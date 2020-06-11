<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="opusWebApp.aDField.home.createOrEditLabel" v-text="$t('opusWebApp.aDField.home.createOrEditLabel')">Create or edit a ADField</h2>
                <div>
                    <div class="form-group" v-if="aDField.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="aDField.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.name')" for="ad-field-name">Name</label>
                        <input type="text" class="form-control" name="name" id="ad-field-name"
                            :class="{'valid': !$v.aDField.name.$invalid, 'invalid': $v.aDField.name.$invalid }" v-model="$v.aDField.name.$model"  required/>
                        <div v-if="$v.aDField.name.$anyDirty && $v.aDField.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.aDField.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.description')" for="ad-field-description">Description</label>
                        <input type="text" class="form-control" name="description" id="ad-field-description"
                            :class="{'valid': !$v.aDField.description.$invalid, 'invalid': $v.aDField.description.$invalid }" v-model="$v.aDField.description.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.hint')" for="ad-field-hint">Hint</label>
                        <input type="text" class="form-control" name="hint" id="ad-field-hint"
                            :class="{'valid': !$v.aDField.hint.$invalid, 'invalid': $v.aDField.hint.$invalid }" v-model="$v.aDField.hint.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.staticText')" for="ad-field-staticText">Static Text</label>
                        <input type="text" class="form-control" name="staticText" id="ad-field-staticText"
                            :class="{'valid': !$v.aDField.staticText.$invalid, 'invalid': $v.aDField.staticText.$invalid }" v-model="$v.aDField.staticText.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.staticField')" for="ad-field-staticField">Static Field</label>
                        <input type="checkbox" class="form-check" name="staticField" id="ad-field-staticField"
                            :class="{'valid': !$v.aDField.staticField.$invalid, 'invalid': $v.aDField.staticField.$invalid }" v-model="$v.aDField.staticField.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.labelOnly')" for="ad-field-labelOnly">Label Only</label>
                        <input type="checkbox" class="form-check" name="labelOnly" id="ad-field-labelOnly"
                            :class="{'valid': !$v.aDField.labelOnly.$invalid, 'invalid': $v.aDField.labelOnly.$invalid }" v-model="$v.aDField.labelOnly.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.showLabel')" for="ad-field-showLabel">Show Label</label>
                        <input type="checkbox" class="form-check" name="showLabel" id="ad-field-showLabel"
                            :class="{'valid': !$v.aDField.showLabel.$invalid, 'invalid': $v.aDField.showLabel.$invalid }" v-model="$v.aDField.showLabel.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.showInGrid')" for="ad-field-showInGrid">Show In Grid</label>
                        <input type="checkbox" class="form-check" name="showInGrid" id="ad-field-showInGrid"
                            :class="{'valid': !$v.aDField.showInGrid.$invalid, 'invalid': $v.aDField.showInGrid.$invalid }" v-model="$v.aDField.showInGrid.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.showInDetail')" for="ad-field-showInDetail">Show In Detail</label>
                        <input type="checkbox" class="form-check" name="showInDetail" id="ad-field-showInDetail"
                            :class="{'valid': !$v.aDField.showInDetail.$invalid, 'invalid': $v.aDField.showInDetail.$invalid }" v-model="$v.aDField.showInDetail.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.gridSequence')" for="ad-field-gridSequence">Grid Sequence</label>
                        <input type="number" class="form-control" name="gridSequence" id="ad-field-gridSequence"
                            :class="{'valid': !$v.aDField.gridSequence.$invalid, 'invalid': $v.aDField.gridSequence.$invalid }" v-model.number="$v.aDField.gridSequence.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.detailSequence')" for="ad-field-detailSequence">Detail Sequence</label>
                        <input type="number" class="form-control" name="detailSequence" id="ad-field-detailSequence"
                            :class="{'valid': !$v.aDField.detailSequence.$invalid, 'invalid': $v.aDField.detailSequence.$invalid }" v-model.number="$v.aDField.detailSequence.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.displayLogic')" for="ad-field-displayLogic">Display Logic</label>
                        <input type="text" class="form-control" name="displayLogic" id="ad-field-displayLogic"
                            :class="{'valid': !$v.aDField.displayLogic.$invalid, 'invalid': $v.aDField.displayLogic.$invalid }" v-model="$v.aDField.displayLogic.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.readOnlyLogic')" for="ad-field-readOnlyLogic">Read Only Logic</label>
                        <input type="text" class="form-control" name="readOnlyLogic" id="ad-field-readOnlyLogic"
                            :class="{'valid': !$v.aDField.readOnlyLogic.$invalid, 'invalid': $v.aDField.readOnlyLogic.$invalid }" v-model="$v.aDField.readOnlyLogic.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.writable')" for="ad-field-writable">Writable</label>
                        <input type="checkbox" class="form-check" name="writable" id="ad-field-writable"
                            :class="{'valid': !$v.aDField.writable.$invalid, 'invalid': $v.aDField.writable.$invalid }" v-model="$v.aDField.writable.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.columnNo')" for="ad-field-columnNo">Column No</label>
                        <input type="number" class="form-control" name="columnNo" id="ad-field-columnNo"
                            :class="{'valid': !$v.aDField.columnNo.$invalid, 'invalid': $v.aDField.columnNo.$invalid }" v-model.number="$v.aDField.columnNo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.columnSpan')" for="ad-field-columnSpan">Column Span</label>
                        <input type="number" class="form-control" name="columnSpan" id="ad-field-columnSpan"
                            :class="{'valid': !$v.aDField.columnSpan.$invalid, 'invalid': $v.aDField.columnSpan.$invalid }" v-model.number="$v.aDField.columnSpan.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.updatable')" for="ad-field-updatable">Updatable</label>
                        <input type="checkbox" class="form-check" name="updatable" id="ad-field-updatable"
                            :class="{'valid': !$v.aDField.updatable.$invalid, 'invalid': $v.aDField.updatable.$invalid }" v-model="$v.aDField.updatable.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.alwaysUpdatable')" for="ad-field-alwaysUpdatable">Always Updatable</label>
                        <input type="checkbox" class="form-check" name="alwaysUpdatable" id="ad-field-alwaysUpdatable"
                            :class="{'valid': !$v.aDField.alwaysUpdatable.$invalid, 'invalid': $v.aDField.alwaysUpdatable.$invalid }" v-model="$v.aDField.alwaysUpdatable.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.copyable')" for="ad-field-copyable">Copyable</label>
                        <input type="checkbox" class="form-check" name="copyable" id="ad-field-copyable"
                            :class="{'valid': !$v.aDField.copyable.$invalid, 'invalid': $v.aDField.copyable.$invalid }" v-model="$v.aDField.copyable.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.defaultValue')" for="ad-field-defaultValue">Default Value</label>
                        <input type="text" class="form-control" name="defaultValue" id="ad-field-defaultValue"
                            :class="{'valid': !$v.aDField.defaultValue.$invalid, 'invalid': $v.aDField.defaultValue.$invalid }" v-model="$v.aDField.defaultValue.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.formatPattern')" for="ad-field-formatPattern">Format Pattern</label>
                        <input type="text" class="form-control" name="formatPattern" id="ad-field-formatPattern"
                            :class="{'valid': !$v.aDField.formatPattern.$invalid, 'invalid': $v.aDField.formatPattern.$invalid }" v-model="$v.aDField.formatPattern.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.active')" for="ad-field-active">Active</label>
                        <input type="checkbox" class="form-check" name="active" id="ad-field-active"
                            :class="{'valid': !$v.aDField.active.$invalid, 'invalid': $v.aDField.active.$invalid }" v-model="$v.aDField.active.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.adOrganization')" for="ad-field-adOrganization">Ad Organization</label>
                        <select class="form-control" id="ad-field-adOrganization" name="adOrganization" v-model="$v.aDField.adOrganizationId.$model" required>
                            <option v-if="!aDField.adOrganizationId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDOrganizationOption.id" v-for="aDOrganizationOption in aDOrganizations" :key="aDOrganizationOption.id">{{aDOrganizationOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDField.adOrganizationId.$anyDirty && $v.aDField.adOrganizationId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDField.adOrganizationId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.adReference')" for="ad-field-adReference">Ad Reference</label>
                        <select class="form-control" id="ad-field-adReference" name="adReference" v-model="aDField.adReferenceId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="aDReferenceOption.id" v-for="aDReferenceOption in aDReferences" :key="aDReferenceOption.id">{{aDReferenceOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.adColumn')" for="ad-field-adColumn">Ad Column</label>
                        <select class="form-control" id="ad-field-adColumn" name="adColumn" v-model="$v.aDField.adColumnId.$model" required>
                            <option v-if="!aDField.adColumnId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDColumnOption.id" v-for="aDColumnOption in aDColumns" :key="aDColumnOption.id">{{aDColumnOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDField.adColumnId.$anyDirty && $v.aDField.adColumnId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDField.adColumnId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.adValidationRule')" for="ad-field-adValidationRule">Ad Validation Rule</label>
                        <select class="form-control" id="ad-field-adValidationRule" name="adValidationRule" v-model="aDField.adValidationRuleId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="adValidationRuleOption.id" v-for="adValidationRuleOption in adValidationRules" :key="adValidationRuleOption.id">{{adValidationRuleOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('opusWebApp.aDField.adTab')" for="ad-field-adTab">Ad Tab</label>
                        <select class="form-control" id="ad-field-adTab" name="adTab" v-model="$v.aDField.adTabId.$model" required>
                            <option v-if="!aDField.adTabId" v-bind:value="null" selected></option>
                            <option v-bind:value="aDTabOption.id" v-for="aDTabOption in aDTabs" :key="aDTabOption.id">{{aDTabOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.aDField.adTabId.$anyDirty && $v.aDField.adTabId.$invalid">
                        <small class="form-text text-danger" v-if="!$v.aDField.adTabId.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.aDField.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ad-field-update.component.ts">
</script>
