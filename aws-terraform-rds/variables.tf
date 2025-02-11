variable "username" {
  description = "The master username for the database."
  type        = string
  sensitive   = true
}
 
variable "password" {
  description = "The master password for the database."
  type        = string
  sensitive   = true
}