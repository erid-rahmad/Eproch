/**
 * Interface of the data schema.
 */
export interface IDataSchema {
  /**
   * https://json-schema.org/understanding-json-schema/reference/type.html
   */
  type?: string

  /**
   * The length of a string can be constrained using the minLength and maxLength keywords.
   * For both keywords, the value must be a non-negative number.
   * https://json-schema.org/understanding-json-schema/reference/string.html#length
   */
  minLength?: number
  maxLength?: number

  /**
   * Ranges of numbers are specified using a combination of the minimum and maximum keywords,
   * (or exclusiveMinimum and exclusiveMaximum for expressing exclusive range).
   * https://json-schema.org/understanding-json-schema/reference/numeric.html#range
   */
  minimum?: number
  maximum?: number
  exclusiveMinimum?: number | boolean
  exclusiveMaximum?: number | boolean

  /**
   * Numbers can be restricted to a multiple of a given number, using the multipleOf keyword.
   * It may be set to any positive number.
   * https://json-schema.org/understanding-json-schema/reference/numeric.html#multiples
   */
  multipleOf?: number

  /**
   * https://json-schema.org/understanding-json-schema/reference/string.html#regular-expressions
   * https://json-schema.org/understanding-json-schema/reference/regular_expressions.html
   */
  pattern?: string

  /**
   * https://json-schema.org/understanding-json-schema/reference/string.html#format
   */
  format?: string
  definitions?: object
  properties?: object
  required: Array<string>
}

/**
 * Interface of the user interface schema.
 */
export interface IUISchema {
  uiSchema: Array<IField>
}

interface IField {
  /**
   * The custom component or native HTML tag name.
   * https://jarvelov.gitbook.io/vue-form-json-schema/api/vue-form-json-schema/ui-schema/field/component
   */
  component: string

  /**
   * Array of fields.
   * https://jarvelov.gitbook.io/vue-form-json-schema/api/vue-form-json-schema/ui-schema/field/children
   */
  children: Array<IField>

  /**
   * Display the field if some conditions met.
   * https://jarvelov.gitbook.io/vue-form-json-schema/api/vue-form-json-schema/ui-schema/field/display-options
   */
  displayOptions: IDisplayOption
}

interface IDisplayOption {
  schema: IDataSchema
}