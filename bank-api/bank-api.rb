require 'sinatra'
require_relative 'bank'
require_relative 'kafka-sender'

set :port, 8070
get '/bank/list' do
    k = Kafkasender.new
    b = BankHandler.new
    k.log("/bank/list")
    b.list
  end
get '/bank/find.name' do
    k = Kafkasender.new
    b = BankHandler.new
    k.log("/bank/find.name?name=#{params['name']}")
    b.find_name(params['name'])
  end
get '/bank/find.key' do
    k = Kafkasender.new
    b = BankHandler.new
    k.log("/bank/find.key?key=#{params['key']}")
    b.find_key(params['key'])
  end