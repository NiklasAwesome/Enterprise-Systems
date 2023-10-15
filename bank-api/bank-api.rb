require 'sinatra'
require_relative 'bank'

set :port, 8070
get '/bank/list' do
    b = BankHandler.new
    b.list
  end
get '/bank/find.name' do
    b = BankHandler.new
    b.find_name(params['name'])
  end
get '/bank/find.key' do
    b = BankHandler.new
    b.find_key(params['key'])
  end